package net.chensee.base.ext;

import com.alibaba.fastjson.serializer.PropertyFilter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xx
 * @program base
 * @date 2019-09-23 17:03
 * @description
 */
public class JsonValuePerRequestFilter implements PropertyFilter {
    @Override
    public boolean apply(Object obj, String property, Object value) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {

            return new OncePerRequestPropertyFilter(obj, property, value, ((ServletRequestAttributes) requestAttributes).getRequest()).filter();
        }
        return true;
    }
}
