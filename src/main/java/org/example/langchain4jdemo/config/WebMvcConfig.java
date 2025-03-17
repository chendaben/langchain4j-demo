package org.example.langchain4jdemo.config;

import org.example.langchain4jdemo.interceptor.AccessLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AccessLogInterceptor accessLogInterceptor;

    public WebMvcConfig(AccessLogInterceptor accessLogInterceptor) {
        this.accessLogInterceptor = accessLogInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLogInterceptor)
                .addPathPatterns("/**"); // Apply to all paths
    }
}
