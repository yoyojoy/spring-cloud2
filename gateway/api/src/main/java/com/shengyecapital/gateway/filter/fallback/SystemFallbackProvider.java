package com.shengyecapital.gateway.filter.fallback;

import com.alibaba.fastjson.JSON;
import com.shengyecapital.common.constants.ResponseMsgConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务异常处理
 *
 * @author long.luo
 */
@Slf4j
@Component
public class SystemFallbackProvider implements FallbackProvider {

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                CustomResponseBody responseBody = new CustomResponseBody();
                responseBody.setCode("1000");
                responseBody.setMessage(ResponseMsgConstants.COMMON_ERROR);
                log.info("异常的路由服务{}", route, cause);
                return new ByteArrayInputStream(JSON.toJSONString(responseBody).getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                //设置头
                httpHeaders.set("Cache-Control", "no-store");
                httpHeaders.set("Pragma", "no-cache");
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return httpHeaders;
            }
        };
    }
}
