package net.chensee.base.component;

import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.user.vo.UserDetailsAllVo;
import net.chensee.base.common.ObjectResponse;
import net.chensee.base.component2.UserCacheComponent;
import net.chensee.base.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xx
 * @program base
 * @date 2019-07-09 15:32
 * @description 当用户登录系统成功后则会进入到此类并执行相关业务
 */
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获得授权后可得到用户信息(非jwt 方式)
        log.debug("=========================== MyAuthenticationSuccessHandler ===========================");

        log.info("登录成功！");
        // User userDetails =  (User) authentication.getPrincipal();

        //获得授权后可得到用户信息(jwt 方式)
        UserDetailsAllVo userDetails = (UserDetailsAllVo) authentication.getPrincipal();
        //将身份 存储到SecurityContext里
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        // request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        request.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        //使用jwt生成token 用于权限效验
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        StringBuffer msg = new StringBuffer("用户：");
        msg.append(userDetails.getUsername()).append(" 成功登录系统.");
        log.info(msg.toString());
        msg.append("access token :").append(accessToken);
        // 设置 accessToken
        // loginVo.setToken(accessToken);
        //将登录人信息放在缓存中
        userCacheComponent.saveUserInfo(accessToken, refreshToken, userDetails);

        // TODO 用户用户登陆成功后的返回内容
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", tokenHead + accessToken);
        map.put("refreshToken", tokenHead + refreshToken);
        map.put("userId", userDetails.getId().toString());
        map.put("loginName", userDetails.getLoginName());
        map.put("userName", userDetails.getUsername());
        map.put("depts", userDetails.getDeptVos());
        map.put("expireTime", System.currentTimeMillis() + (expiration - 300) * 1000);
        // map.put("msage",msg.toString());
        String s = JsonUtil.toStr(ObjectResponse.ok(map, msg.toString()));

        userCacheComponent.saveLoginInfo(accessToken, map);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(s);
        response.getWriter().flush();
    }

}