package com.shengyecapital.common.advice;

import com.alibaba.fastjson.JSON;
import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import com.shengyecapital.common.dto.common.PageResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice("com.shengyecapital.business.controller")
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // already processed by ExceptionHandler
        return (returnType.getContainingClass() != CustomExceptionAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof CustomResponseBody) {
            return body;
        }

        CustomResponseBody<Object> responseBody = new CustomResponseBody<>(ResponseCodeConstants.OK);
        if (body == null) {
            // when return type of controller method is void
        } else if (body instanceof PageResult) {
            responseBody.setPageResult((PageResult) body);
        } else if (body instanceof String){
            return JSON.toJSONString(body);
        } else {
            responseBody.setData(body);
        }

        return responseBody;
    }

}
