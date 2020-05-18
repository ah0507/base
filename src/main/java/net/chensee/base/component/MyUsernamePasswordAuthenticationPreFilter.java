package net.chensee.base.component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xx
 * @program base
 * @date 2019-07-09 15:27
 * @description 登陆前的认证
 * 调用登录接口时会进入到此类的attemptAuthentication方法 进行相关校验操作
 */
@Slf4j
public class MyUsernamePasswordAuthenticationPreFilter extends UsernamePasswordAuthenticationFilter {

    @Setter
    @Getter
    private String verifyCodeParameter = "verifyCode";

    public MyUsernamePasswordAuthenticationPreFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        this.setFilterProcessesUrl("/login");  // 这句代码很重要，设置登陆的url 要和 WebSecurityConfig 配置类中的.loginProcessingUrl("/login") 一致，如果不配置则无法执行 重写的attemptAuthentication 方法里面而是执行了父类UsernamePasswordAuthenticationFilter的attemptAuthentication（）
        this.setAuthenticationManager(authenticationManager);   // AuthenticationManager 是必须的
        this.setAuthenticationSuccessHandler(successHandler);   // 设置自定义登陆成功后的业务处理
        this.setAuthenticationFailureHandler(failureHandler);   // 设置自定义登陆失败后的业务处理

        this.setUsernameParameter("loginName");
        this.setPasswordParameter("loginPass");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("=========================== MyUsernamePasswordAuthenticationPreFilter ===========================");

        //校验验证码
        String verifyCode = obtainVerifyCode(request);
        if (!checkValidateCode(verifyCode)) {
            // ResultUtil.writeJavaScript(response,ErrorCodeEnum.FAIL,"验证码错误.");
            System.out.println("验证码错误.");
            return null;
        }
        return super.attemptAuthentication(request, response);
    }

    protected String obtainVerifyCode(HttpServletRequest request) {
        return request.getParameter(this.getVerifyCodeParameter());
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return super.obtainPassword(request);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return super.obtainUsername(request);
    }

    /**
     * 验证 验证码是否正确
     *
     * @param verifyCode
     * @return
     */
    private boolean checkValidateCode(String verifyCode) {
        if (verifyCode == null || !verifyCode.trim().equals("123456")) {
            return false;
        }
        return true;
    }
}