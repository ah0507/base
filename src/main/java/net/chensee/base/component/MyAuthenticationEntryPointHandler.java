package net.chensee.base.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xx
 * @program base
 * @date 2019-07-09 15:35
 * @description 当检测到用户访问系统资源认证失败时则会进入到此类并执行相关业务
 */
@Slf4j
public class MyAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        StringBuffer msg = new StringBuffer("请求访问: ");
        msg.append(httpServletRequest.getRequestURI()).append(" 接口，无法访问系统资源.");
        log.info(msg.toString());
        // ResultUtil.writeJavaScript(httpServletResponse,ErrorCodeEnum.LOGIN_WITHOUT,msg.toString());

        log.error("认证失败");

      /*  boolean ajaxRequest = HttpUtils.isAjaxRequest(httpServletRequest);
        if (ajaxRequest){
            //如果是ajax请求 则返回自定义错误
            ResultUtil.writeJavaScript(httpServletResponse,ErrorCodeEnum.LOGIN,map);
        }else {
            // 非ajax请求 则跳转到指定的403页面
            //此处省略...................
        }*/
    }
}
