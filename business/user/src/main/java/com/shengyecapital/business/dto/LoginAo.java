package com.shengyecapital.business.dto;

import lombok.Data;

/**
 * 登录相关参数
 *
 * @author long.luo
 * @date 2018/12/26 13:39
 */
@Data
public class LoginAo {
    /**
     * password和ldap认证模式下用户名
     */
    private String username;
    /**
     * password和ldap认证模式下密码
     */
    private String password;

    /**
     * 验证码（客户端认证模式不传）
     */
    private String captchaCode;

    /**
     * 验证码key（客户端认证模式不传）
     */
    private String captchaKey;
}
