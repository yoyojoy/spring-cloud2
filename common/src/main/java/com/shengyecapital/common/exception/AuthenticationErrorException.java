package com.shengyecapital.common.exception;

import com.shengyecapital.common.constants.ResponseCodeConstants;

/**
 * 认证异常
 *
 * @author long.luo
 * @date 2018/12/27 9:46
 */
public class AuthenticationErrorException extends CustomException {

    public AuthenticationErrorException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return ResponseCodeConstants.UNAUTHORIZED;
    }
}
