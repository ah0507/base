package net.chensee.base.component;

import net.chensee.base.action.user.vo.UserDetailsVo;
import net.chensee.base.component2.UserCacheComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xx
 * @program base
 * @date 2019-07-09 15:29
 * @description 当用户退出系统成功后则会进入到此类并执行相关业务
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserCacheComponent userCacheComponent;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (authentication == null) {
            return;
        }

        //根据token清空redis
        /*String userKey =  RedisKeys.USER_KEY;
        String token = userUtils.getUserToken(httpServletRequest);
        redisUtil.hdel(userKey,token);
        SecurityContextHolder.clearContext();  //清空上下文
        httpServletRequest.getSession().removeAttribute("SPRING_SECURITY_CONTEXT"); // 从session中移除
        //退出信息插入日志记录表中
        ResultUtil.writeJavaScript(httpServletResponse,ErrorCodeEnum.SUCCESS,"退出系统成功.");*/

        UserDetailsVo userDetails = (UserDetailsVo) authentication.getPrincipal();
        Long id = userDetails.getId();
        userCacheComponent.removeUserInfo(id);

        System.out.println("退出系统成功！");
    }
}
