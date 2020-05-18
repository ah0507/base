package net.chensee.base.component;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * @author xx
 * @program base
 * @date 2019-08-11 15:05
 * @description 简单资源授权，根据资源名称和请求方式
 */
public final class SimpleResourceGrantedAuthority implements GrantedAuthority {
    private final String resourceName;
    private final String method;

    public SimpleResourceGrantedAuthority(String resourceName, String method) {
        Assert.hasText(resourceName, "resourceName is required");
        Assert.hasText(method, "request method is required");
        this.resourceName = resourceName;
        this.method = method.toLowerCase();
    }

    @Override
    public String getAuthority() {
        return this.resourceName + "_" + this.method;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof GrantedAuthority ? this.getAuthority().equals(((GrantedAuthority) obj).getAuthority()) : false;
        }
    }

    @Override
    public int hashCode() {
        return getAuthority().hashCode();
    }

    @Override
    public String toString() {
        return this.getAuthority();
    }
}
