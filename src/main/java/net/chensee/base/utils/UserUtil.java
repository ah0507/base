package net.chensee.base.utils;

import net.chensee.base.action.user.vo.UserDetailsAllVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : shibo
 * @date : 2019/7/9 13:38
 */
public class UserUtil {
    public static UserDetailsAllVo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsAllVo) authentication.getPrincipal();
    }
}
