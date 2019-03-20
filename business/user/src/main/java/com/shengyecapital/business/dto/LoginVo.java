package com.shengyecapital.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 登录成功VO
 *
 * @author long.luo
 * @date 2018/12/26 13:54
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginVo {

    /**
     * token
     */
    private String accessToken;

    /**
     * token类型
     */
    private String tokenType;

    /**
     * 刷新token用
     */
    private String refreshToken;

    /**
     * token生效时间
     */
    private Integer expiresIn;

    /**
     * 权限范围（暂时无用）
     */
    private String scope;
}
