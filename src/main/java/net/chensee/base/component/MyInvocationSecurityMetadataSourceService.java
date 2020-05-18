package net.chensee.base.component;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.resource.business.ResourceBus;
import net.chensee.base.action.resource.vo.ResourceVo;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author : xx
 * @program: base
 * @create: 2019-07-01 16:50
 * @description: 获取所有的资源权限，以便后续校验
 */
@Slf4j
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Setter
    private ResourceBus resourceBus;

    private HashMap<String, Collection<SimpleSecurityConfig>> permitMap = new HashMap<>(200);

    /**
     * 返回请求的地址所需要的资源名称
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (null == permitMap || permitMap.size() == 0) {
            refreshResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        String method = request.getMethod();
        Collection<ConfigAttribute> cas = new ArrayList<>();
        for (Iterator<String> it = permitMap.keySet().iterator(); it.hasNext(); ) {
            String url = it.next();
            if (url != null && !url.trim().equals("") && new AntPathRequestMatcher(url).matches(request)) {
                Collection<SimpleSecurityConfig> configAttributes = permitMap.get(url);
                // 相同的请求地址，相同的请求方式
                for (SimpleSecurityConfig ssc : configAttributes) {
                    String methodNeed = ssc.getMethod();
                    if (method.toLowerCase().equals(methodNeed)) {
                        cas.add(ssc);
                    }
                }
            }
        }
        return cas;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 初始化 所有资源 对应的角色
     * <p>
     * 提取系统中的所有权限，加载所有url和权限（或角色）的对应关系
     * <p>
     * 需要在web容器启动就会执行
     * 也可以将接口向外暴露，进行手动刷新
     */
    public void refreshResourceDefine() {
        // 加载用户的所有资源
        List<ResourceVo> resources = resourceBus.getAllResource();

        for (ResourceVo resourceVo : resources) {
            Long id = resourceVo.getId();
            String url = resourceVo.getPath();
            String name = resourceVo.getName();
            String method = resourceVo.getMethod();

            if (id == null || name == null || name.isEmpty() || method == null || method.isEmpty()) {
                continue;
            }
            SimpleSecurityConfig role = new SimpleSecurityConfig(id, name, method);

            if (permitMap.containsKey(url)) {
                permitMap.get(url).add(role);
            } else {
                List<SimpleSecurityConfig> list = new ArrayList<>();
                list.add(role);
                permitMap.put(url, list);
            }
        }

    }

}