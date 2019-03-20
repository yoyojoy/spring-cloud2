package com.shengyecapital.common.exception;

import com.shengyecapital.common.constants.ResponseCodeConstants;

/**
 * 服务端产生的异常
 */
public class ServerErrorException extends CustomException {

    public ServerErrorException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return ResponseCodeConstants.SERVER_ERROR;
    }

}
