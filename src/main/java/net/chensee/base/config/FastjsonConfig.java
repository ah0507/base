// package net.chensee.base.config;
//
// import com.alibaba.fastjson.serializer.SerializerFeature;
// import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
// import com.alibaba.fastjson.support.config.FastJsonConfig;
// import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
// import net.chensee.base.constants.BaseConstants;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.MediaType;
// import org.springframework.http.converter.HttpMessageConverter;
// import org.springframework.http.converter.StringHttpMessageConverter;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
// import javax.servlet.http.HttpServletRequest;
// import java.nio.charset.Charset;
// import java.util.*;
//
// /**
//  * @author xx
//  * @program base
//  * @date 2019-09-05 17:31
//  * @description
//  */
// // @Configuration
// public class FastjsonConfig implements WebMvcConfigurer {
//
//     /**
//      * 替换框架json为fastjson
//      */
//     @Override
//     public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//         FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//         FastJsonConfig fastJsonConfig = new FastJsonConfig();
//         fastJsonConfig.setSerializerFeatures(
//                 SerializerFeature.PrettyFormat,
//                 SerializerFeature.WriteMapNullValue,
//                 SerializerFeature.WriteDateUseDateFormat
//                 // SerializerFeature.WriteNullNumberAsZero,
//                 // SerializerFeature.WriteNullStringAsEmpty
//         );
//         fastJsonConfig.setSerializeFilters(new CusSimplePropertyPreFilter());
//         // 处理中文乱码问题
//         List<MediaType> fastMediaTypes = new ArrayList<>();
//         fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//         fastConverter.setSupportedMediaTypes(fastMediaTypes);
//         fastConverter.setFastJsonConfig(fastJsonConfig);
//
//         //处理字符串, 避免直接返回字符串的时候被添加了引号
//         StringHttpMessageConverter smc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//         converters.add(smc);
//
//         converters.add(fastConverter);
//     }
//
//     public final class CusSimplePropertyPreFilter extends SimplePropertyPreFilter {
//         public CusSimplePropertyPreFilter() {
//             super();
//
//             init();
//         }
//
//         private void init() {
//             ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//             HttpServletRequest request = servletRequestAttributes.getRequest();
//             Object exs = request.getAttribute(BaseConstants.ResourceExcludesKey);
//             add(exs, this.getExcludes());
//             Object ins = request.getAttribute(BaseConstants.ResourceIncludesKey);
//             add(ins, this.getIncludes());
//         }
//
//         private void add(Object source, Set<String> target) {
//             if (source instanceof Collection) {
//                 target.addAll((Collection<String>)source);
//             } else if (source instanceof String) {
//                 target.add((String)source);
//             }
//         }
//     }
// }
