package com.shengyecapital.common.client.auth;

import com.alibaba.fastjson.JSON;
import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author long.luo
 * @date 2018/12/27 14:37
 */
@Slf4j
@Component
public class AuthClientFallbackFactory implements FallbackFactory<AuthClient> {
    @Override
    public AuthClient create(Throwable cause) {
        return (grantType, clientId, clientSecret, scope, username, password) -> {
            log.error("认证异常", cause);
            CustomResponseBody<String> responseBody = new CustomResponseBody<>();
            responseBody.setCode(ResponseCodeConstants.UNAUTHORIZED);
            responseBody.setMessage(cause.getMessage());
            return JSON.toJSONString(responseBody);
        };
    }
}
