package com.warp.unicorn.adapter.config.mvc;

import com.warp.unicorn.adapter.config.mvc.interceptors.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final RequestInterceptor requestIdInterceptor;

    @Value("${spring.application.name}")
    private String rootPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(requestIdInterceptor)
                .addPathPatterns("/" + rootPath + "/**")
                .excludePathPatterns("/" + rootPath + "/live")
                .excludePathPatterns("/" + rootPath + "/read");
    }

}
