package net.chensee.base.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.user.vo.UserDetailsAllVo;
import net.chensee.base.action.user.vo.UserDetailsVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : xx
 * @program: base
 * @create: 2019-07-01 16:01
 * @description: jwt工具类  提供校验toeken 、生成token、根据token获取用户等方法
 */
@Data
@Slf4j
public class JwtTokenUtil implements Serializable {

    public static final String ROLE_REFRESH_TOKEN = "RSH_TKO";
    public static final String ROLE_ACCESS_TOKEN = "ACC_SYS";
    private static final String TOKEN_TYPE_REFRESH = "rsh";
    private static final String TOKEN_TYPE_ACCESS = "acc";
    private static final String CLAIM_KEY_TOKEN_TYPE = "type";
    private static final String CLAIM_KEY_USER_ID = "user_id";
    //签名方式
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    /**
     * 密匙
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * accessToken 过期时间
     */
    @Value("${jwt.access_token}")
    private Long accessTokenExpiration;
    /**
     * refresh token 过期时间
     */
    @Value("${jwt.refresh_token}")
    private Long refreshTokenExpiration;
    /**
     * 过期时间
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息 生成token
     *
     * @param userDetails
     * @return
     */
    public String generateAccessToken(UserDetailsVo userDetails) {
        return generateToken(UUID.randomUUID().toString(), userDetails.getUsername(), generateClaims(userDetails, TOKEN_TYPE_ACCESS), userDetails.getIssuedAt(), accessTokenExpiration);
    }

    /**
     * 根据用户信息 获取 refresh token
     *
     * @param userDetails
     * @return
     */
    public String generateRefreshToken(UserDetailsVo userDetails) {
        return generateToken(UUID.randomUUID().toString(), userDetails.getUsername(), generateClaims(userDetails, TOKEN_TYPE_REFRESH), userDetails.getIssuedAt(), refreshTokenExpiration);
    }

    /**
     * 根据refresh token 重新获取 access token
     *
     * @param refreshToken
     * @return
     */
    public String refreshAccessToken(String refreshToken) {
        final Claims claims = parseToken(refreshToken);
        // refresh token 过期
        if (claims.getExpiration().after(new Date())) {
            return null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null || ! (authentication.getPrincipal() instanceof UserDetailsAllVo)) {
            return null;
        }

        UserDetailsAllVo userDetailsAllVo = (UserDetailsAllVo) authentication.getPrincipal();
        if (userDetailsAllVo.getId().equals(Long.valueOf(claims.get(CLAIM_KEY_USER_ID).toString())) && userDetailsAllVo.getUsername().equals(claims.getSubject())) {
            Object tokenType = claims.get(CLAIM_KEY_TOKEN_TYPE);
            if (tokenType != null && tokenType.equals(TOKEN_TYPE_REFRESH)) {
                userDetailsAllVo.setIssuedAt(new Date());
                return generateToken(UUID.randomUUID().toString(), claims.getSubject(), generateClaims(userDetailsAllVo, TOKEN_TYPE_ACCESS), userDetailsAllVo.getIssuedAt(), accessTokenExpiration);
            }
        }
        return null;
    }

    /**
     * 生成Claims
     *
     * @param userDetails
     * @param type
     * @return
     */
    private Map<String, Object> generateClaims(UserDetailsVo userDetails, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_ID, userDetails.getId());
        claims.put(CLAIM_KEY_TOKEN_TYPE, type);
        return claims;
    }

    /**
     * 生成token的实际处理类
     *
     * @param id         token唯一标识码
     * @param subject    token的标题
     * @param claims     token参数
     * @param expiration token过期时间 : second
     * @return
     */
    private String generateToken(String id, String subject, Map<String, Object> claims, Date issuedAt, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(id)
                .setIssuedAt(issuedAt)
                .setExpiration(new Date(issuedAt.getTime() + expiration * 1000))
                //系统时间之前的token都是不可以被承认的
                //.setNotBefore(now)
                //数字签名
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    /**
     * 根据token 获取用户信息
     *
     * @param token
     * @return
     */
    public UserDetailsVo getUserFromToken(String token) {
        UserDetailsVo jwtUserDetails;
        try {
            final Claims claims = parseToken(token);
            Long userId = Long.valueOf(claims.get(CLAIM_KEY_USER_ID).toString());
            String username = claims.getSubject();
            long expiration = (claims.getExpiration().getTime() - claims.getIssuedAt().getTime()) / 1000;
            return new UserDetailsVo(userId, username, null, null, expiration, claims.getIssuedAt());
        } catch (Exception e) {
            log.error("getUserFromToken", e);
        }
        return null;
    }

    /***
     * 解析token 信息
     * @param token
     * @return
     */
    private Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    // 签名的key
                    .setSigningKey(secret)
                    // 签名token
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    /**
     * token 是否过期
     *
     * @param token
     * @return
     */
    private Boolean isTokenNonExpired(String token) {
        UserDetailsVo userFromToken = getUserFromToken(token);
        return userFromToken.isAccountNonExpired();
    }

}

