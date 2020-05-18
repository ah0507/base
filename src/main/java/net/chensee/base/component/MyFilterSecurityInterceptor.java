package net.chensee.base.component;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author : xx
 * @program: base
 * @create: 2019-07-01 16:49
 * @description: 继承AbstractSecurityInterceptor、实现Filter是必须的
 * 首先，登陆后，每次访问资源都会被这个拦截器拦截，会执行doFilter这个方法，这个方法调用了invoke方法，其中fi断点显示是一个url
 * 最重要的是beforeInvocation这个方法，它首先会调用MyInvocationSecurityMetadataSource类的getAttributes方法获取被拦截url所需的权限
 * 在调用MyAccessDecisionManager类decide方法判断用户是否具有权限,执行完后就会执行下一个拦截器
 */
@Slf4j
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Setter
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("=========================== MyFilterSecurityInterceptor ===========================");

        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {

        return this.securityMetadataSource;
    }


}
