package com.li.Anntation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
public class InterceptorConfig implements WebMvcConfigurer {
//    @Resource
//    TokenInterceptor tokenInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 设置所有的路径都要进行拦截，除了/test/login
//        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/test/login");
//    }
}