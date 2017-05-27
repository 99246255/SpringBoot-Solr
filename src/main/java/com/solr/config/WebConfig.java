//package com.solr.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
//        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
//        super.addResourceHandlers(registry);
//    }
//
//    /**
//     * fastJson相关设置
//     */
//    private FastJsonConfig getFastJsonConfig() {
//
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        // 在serializerFeatureList中添加转换规则
//        List<SerializerFeature> serializerFeatureList = new ArrayList<SerializerFeature>();
//        serializerFeatureList.add(SerializerFeature.PrettyFormat);
//        serializerFeatureList.add(SerializerFeature.WriteMapNullValue);
//        serializerFeatureList.add(SerializerFeature.WriteNullStringAsEmpty);
//        serializerFeatureList.add(SerializerFeature.WriteNullListAsEmpty);
//        serializerFeatureList.add(SerializerFeature.DisableCircularReferenceDetect);
//        serializerFeatureList.add(SerializerFeature.WriteEnumUsingToString);
//        SerializerFeature[] serializerFeatures = serializerFeatureList.toArray(new SerializerFeature[serializerFeatureList.size()]);
//        fastJsonConfig.setSerializerFeatures(serializerFeatures);
//
//        return fastJsonConfig;
//    }
//
//    /**
//     * fastJson相关设置
//     */
//    private FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {
//
//        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter4();
//
//        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//        supportedMediaTypes.add(MediaType.parseMediaType("text/html;charset=UTF-8"));
//        supportedMediaTypes.add(MediaType.parseMediaType("application/json"));
//
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//        fastJsonHttpMessageConverter.setFastJsonConfig(getFastJsonConfig());
//
//        return fastJsonHttpMessageConverter;
//    }
//
//    /**
//     * 添加fastJsonHttpMessageConverter到converters
//     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(fastJsonHttpMessageConverter());
//    }
//
//}