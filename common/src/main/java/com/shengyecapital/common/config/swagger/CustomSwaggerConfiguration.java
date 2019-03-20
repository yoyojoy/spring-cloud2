package com.shengyecapital.common.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;

@Configuration
@ConditionalOnClass({EnableSwagger2.class})
public class CustomSwaggerConfiguration {

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shengyecapital.business.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Timestamp.class, String.class)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sheng Ye REST APIs")
                .description("Sheng Ye提供的API接口")
                .version("1.0.0")
                .build();
    }

}
