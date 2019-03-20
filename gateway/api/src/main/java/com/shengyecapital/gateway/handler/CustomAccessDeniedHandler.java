package com.shengyecapital.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义OAuth2 权限异常
 *
 * @author long.luo
 * @date 2018/12/17 15:49
 */
@Component("customAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.SC_OK);
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        CustomResponseBody responseBody = new CustomResponseBody(ResponseCodeConstants.UNPERMISSION, "抱歉，您的权限不足");
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(responseBody));
    }
}
