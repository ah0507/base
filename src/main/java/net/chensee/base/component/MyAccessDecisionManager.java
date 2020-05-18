package net.chensee.base.component;

import lombok.extern.slf4j.Slf4j;
import net.chensee.base.constants.BaseConstants;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

/**
 * @program: base
 * @author : xx
 * @create: 2019-07-01 16:47
 * @description: 决策器
 */
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     *
     * @param authentication 包含了当前的用户信息，包括拥有的权限。这里的权限来源就是前面登录时UserDetailsService中设置的authorities。
     * @param object  就是FilterInvocation对象，可以得到request等web资源
     * @param configAttributes configAttributes是本次访问需要的权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes || 0 >= configAttributes.size()) {
            // return;
            throw new AccessDeniedException("当前访问没有权限，请检查权限配置");
        } else {
            String needRole;
            for(Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
                ConfigAttribute next = iter.next();
                needRole = next.getAttribute();

                for(GrantedAuthority ga : authentication.getAuthorities()) {
                    if(needRole.trim().equals(ga.getAuthority().trim())) {
                        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                        if (requestAttributes != null) {
                            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                            SimpleSecurityConfig ssc = (SimpleSecurityConfig) next;
                            request.setAttribute(BaseConstants.CurrentResourceIdKey, ssc.getResourceId());
                        }

                        return;
                    }
                }
            }
            throw new AccessDeniedException("当前访问没有权限");
        }

    }

    /**
     * 表示此AccessDecisionManager是否能够处理传递的ConfigAttribute呈现的授权请求
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * 表示当前AccessDecisionManager实现是否能够为指定的安全对象（方法调用或Web请求）提供访问控制决策
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
