package net.chensee.base.ext;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.constants.BaseConstants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xx
 * @program base
 * @date 2019-09-11 14:17
 * @description
 */
public class MySimplePropertyPreFilter implements PropertyPreFilter {

    private final SimplePropertyPreFilter real = new SimplePropertyPreFilter();

    public MySimplePropertyPreFilter() {
        reset();
    }

    @Override
    public boolean apply(JSONSerializer jsonSerializer, Object o, String s) {
        retry();
        return real.apply(jsonSerializer, o, s);
    }

    private void reset() {
        real.getIncludes().clear();
        real.getExcludes().clear();
    }

    private void reassamble() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        ResourceVo resourceVo = (ResourceVo) request.getAttribute(BaseConstants.CurrentResourceKey);
        if (resourceVo != null) {
            real.getIncludes().add("");
            real.getExcludes().add("");
        }
    }

    private void retry() {
        reset();
        reassamble();
    }
}