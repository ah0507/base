package net.chensee.base.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.common.ObjectResponse;
import net.chensee.base.utils.JsonUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xx
 * @program base
 * @date 2019-07-09 15:34
 * @description 当用户登录系统失败后则会进入到此类并执行相关业务
 */
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //用户登录时身份认证未通过
        if (e instanceof BadCredentialsException){
            log.error("用户登录时：用户名或者密码错误.", e);
            respResult(httpServletResponse, ErrorCodeEnum.LOGIN_INCORRECT);
            return ;
        }else if(e instanceof MyAuthenticationException) {
            log.error("用户登录异常", e);
            if (((MyAuthenticationException) e).getExceptionEnum() != null) {
                respResult(httpServletResponse, ((MyAuthenticationException) e).getExceptionEnum());
                return ;
            }
        }
        log.error("用户登录异常", e);
        respResult(httpServletResponse, ErrorCodeEnum.LOGIN_FAIL);
        return ;

    }

    private void respResult(HttpServletResponse response, ErrorCodeEnum errorCodeEnum) {
        try {
            String s = JsonUtil.toStr(ObjectResponse.fail(errorCodeEnum.code, errorCodeEnum.name()));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(s);
            response.getWriter().flush();
        } catch (JsonProcessingException e) {
            log.error("json 序列化异常", e);
        } catch (IOException e) {
            log.error("向客户端写入消息异常", e);
        }
    }
}
