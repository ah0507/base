package net.chensee.base.action.user.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

/**
 * @author : xx
 * @program: base
 * @create: 2019-07-01 16:27
 * @description: UserDetail
 */
public class UserDetailsVo implements UserDetails {
    /**
     * 用户ID
     */
    @Getter
    @Setter
    private Long id;
    /**
     * 用户登录名
     */
    @Getter
    @Setter
    private String loginName;
    /**
     * 用户登录密码
     */
    @Getter
    @Setter
    private String loginPass;
    @Getter
    @Setter
    private Collection<? extends GrantedAuthority> authorities;
    /**
     * 有效时长
     */
    @Getter
    @Setter
    private long expiration;
    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date issuedAt;

    public UserDetailsVo() {}

    public UserDetailsVo(Long userId, String username, String password, Collection<GrantedAuthority> authorities, long expiration, Date issuedAt) {
        this.setId(userId);
        this.setLoginName(username);
        this.setLoginPass(password);
        this.setAuthorities(authorities);
        this.setExpiration(expiration);
        this.setIssuedAt(issuedAt);
    }

    @Override
    public String getPassword() {
        return loginPass;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        Duration between = Duration.between(Instant.now(), issuedAt.toInstant());
        long seconds = between.getSeconds();
        return seconds <= expiration;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
