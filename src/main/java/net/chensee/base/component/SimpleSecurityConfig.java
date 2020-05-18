package net.chensee.base.component;

import lombok.Getter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.util.Assert;

/**
 * @author xx
 * @program base
 * @date 2019-08-11 15:12
 * @description 简单资源权限
 */
public class SimpleSecurityConfig implements ConfigAttribute {
    @Getter
    private final Long resourceId;
    @Getter
    private final String resourceName;
    @Getter
    private final String method;

    public SimpleSecurityConfig(Long resourceId, String resourceName, String method) {
        Assert.notNull(resourceId, "You must provide a configuration attribute for resource id");
        Assert.hasText(resourceName, "You must provide a configuration attribute for resource name");
        Assert.hasText(method, "You must provide a configuration attribute for request method");
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.method = method.toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConfigAttribute) {
            ConfigAttribute attr = (ConfigAttribute) obj;
            return this.getAttribute().equals(attr.getAttribute());
        } else {
            return false;
        }
    }

    @Override
    public String getAttribute() {
        return this.resourceName + "_" + this.method;
    }

    @Override
    public int hashCode() {
        return this.getAttribute().hashCode();
    }

    @Override
    public String toString() {
        return this.getAttribute();
    }
}