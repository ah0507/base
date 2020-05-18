package net.chensee.base.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.common.ObjectResponse;
import net.chensee.base.utils.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xx
 * @program base
 * @date 2019-07-09 15:52
 * @description
 */
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        StringBuffer msg = new StringBuffer("请求: ");
        msg.append(httpServletRequest.getRequestURI()).append(" 权限不足，无法访问系统资源.");
        log.info(msg.toString());
        respResult(httpServletResponse,ErrorCodeEnum.AUTHORITY, msg.toString());
    }

    private void respResult(HttpServletResponse response, ErrorCodeEnum errorCodeEnum, String msg) {
        try {
            String s = JsonUtil.toStr(ObjectResponse.fail(errorCodeEnum.code, msg));
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
