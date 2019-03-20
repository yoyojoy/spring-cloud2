package com.shengyecapital.common.config.feign;

import com.shengyecapital.common.constants.RequestHeaderConstants;
import com.shengyecapital.common.context.CustomHystrixContext;
import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CustomFeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor customRequestInterceptor() {
        return template -> {
            Integer userId = CustomHystrixContext.getInstance().getUserId();
            if (userId != null) {
                template.header(RequestHeaderConstants.X_USER_ID, userId.toString());
            }
        };
    }

}
