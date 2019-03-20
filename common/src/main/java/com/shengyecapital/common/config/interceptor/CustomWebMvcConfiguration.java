package com.shengyecapital.common.config.interceptor;

import com.shengyecapital.common.interceptor.EnhanceLogInterceptor;
import com.shengyecapital.common.interceptor.PropagateUserIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new EnhanceLogInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new PropagateUserIdInterceptor()).addPathPatterns("/**");
    }

}