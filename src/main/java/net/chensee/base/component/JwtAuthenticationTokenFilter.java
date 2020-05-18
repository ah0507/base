package net.chensee.base.component;

import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.user.vo.UserDetailsVo;
import net.chensee.base.component2.UserCacheComponent;
import net.chensee.base.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * @author : xx
 * @program: base
 * @create: 2019-07-01 16:04
 * @description: 每次请求接口时 就会进入这里验证token 是否合法
 * token 如果用户一直在操作，则token 过期时间会叠加
 * 如果超过设置的过期时间未操作  则token 失效 需要重新登录
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private UserDetailsService userDetailsService;

    //@Autowired
    //private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.expiration}")
    private Long expiration;
    @Autowired
    private UserCacheComponent userCacheComponent;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("=========================== JwtAuthenticationTokenFilter ===========================");
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        // @xx 2019-11-13 为了解决页面跳转，无法写入Header的问题
        if (authHeader == null) {
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies != null && cookies.length >= 1) {
                for (Cookie c : cookies) {
                    if (c != null && this.tokenHeader.equalsIgnoreCase(c.getName())) {
                        authHeader = c.getValue();
                    }
                }
            }
        }
        // @xx 2019-11-13 end

        // 如果已经检验通过的话，代表按地址校验通过
        // 1. 如果按地址检验通过，且token为空的话，直接放行
        // 2. 如果按地址检验通过，且token不为空的话，则需要解析token，请求DB数据
        // 2. 如果按地址不检验通过，抛出异常
        if (!authentication.isAuthenticated()) {
            throw new AccessDeniedException("前面的过滤器未校验成功");
        }
        if (authentication.isAuthenticated() && authHeader == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if (authHeader == null) {
            log.error("token 不存在.");
            throw new MyAuthenticationException("TOKEN_NOT_EXIST");
        }
        if (!authHeader.startsWith(tokenHead)) {
            log.error("token 无效.");
            throw new MyAuthenticationException("TOKEN_INVALID");
        }
        // The part after "Bearer "
        String authToken = authHeader.substring(tokenHead.length());
        log.info("请求" + httpServletRequest.getRequestURI() + "携带的token值：" + authToken);

        httpServletRequest.setAttribute("_accessToken", authToken);
        // 查看redis中的token信息是否过期
        String userInfo = userCacheComponent.getUserInfo(authToken);

        if (userInfo == null) {
            // TODO token 过期 提示用户登录超时 重新登录系统
            throw new MyAuthenticationException("LOGIN_WITHOUT");
        }

        UserDetailsVo userFromToken = jwtTokenUtil.getUserFromToken(authToken);
        // 如果在token过期之前触发接口,我们更新token过期时间，token值不变只更新过期时间
        // 获取token生成时间
        Date createTokenDate = userFromToken.getIssuedAt();
        if (createTokenDate != null) {
            Duration between = Duration.between(Instant.now(), Instant.ofEpochMilli(createTokenDate.getTime()));
            Long differSeconds = between.getSeconds();
            //如果 请求接口时间在token 过期之前 则更新token过期时间  我们可以将用户的token 存放到redis 中,更新redis 的过期时间
            if (expiration < differSeconds) {
                // TODO 更新TOKEN , token 过期
                throw new MyAuthenticationException("TOKEN_EXPIRE");
            }
        }
        log.info("JwtAuthenticationTokenFilter[doFilterInternal] checking authentication " + userFromToken.getUsername());
        //token校验通过
        if (userFromToken.getUsername() == null) {
            log.info("token 无效.");
            throw new MyAuthenticationException("TOKEN_INVALID");
        }
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            //根据account去数据库中查询user数据，足够信任token的情况下，可以省略这一步
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userFromToken.getUsername());

            if (userFromToken.getId().equals(((UserDetailsVo) userDetails).getId())
                    && userFromToken.getUsername().equals(userDetails.getUsername())
                    && userFromToken.isAccountNonExpired()) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        httpServletRequest));
                log.info("JwtAuthenticationTokenFilter[doFilterInternal] authenticated user " + userFromToken.getUsername() + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
            }
        } else {
            log.info("当前请求用户信息：" + JsonUtil.toStr(authentication.getPrincipal()));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}