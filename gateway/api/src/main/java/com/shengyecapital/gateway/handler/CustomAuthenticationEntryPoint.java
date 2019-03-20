package com.shengyecapital.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义OAuth2 token异常
 *
 * @author long.luo
 * @date 2018/12/17 14:33
 */
@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.SC_OK);
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        CustomResponseBody responseBody = new CustomResponseBody(ResponseCodeConstants.UNAUTHORIZED, "您的访问令牌已过期，请重新获取");
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(responseBody));
    }
}
