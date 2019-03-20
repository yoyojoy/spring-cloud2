package com.shengyecapital.common.client.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 认证客户端
 *
 * @author long.luo
 * @date 2018/12/27 14:36
 */
@FeignClient(value = "auth", fallbackFactory = AuthClientFallbackFactory.class)
public interface AuthClient {

    /**
     * 认证处理
     *
     * @param grantType
     * @param clientId
     * @param clientSecret
     * @param scope
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/uaa/oauth/token")
    String authentication(@RequestParam("grant_type") String grantType,
                                                          @RequestParam("client_id") String clientId,
                                                          @RequestParam("client_secret") String clientSecret,
                                                          @RequestParam("scope") String scope,
                                                          @RequestParam("username") String username,
                                                          @RequestParam("password") String password);
}
