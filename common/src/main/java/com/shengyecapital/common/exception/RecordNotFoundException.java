package com.shengyecapital.common.exception;

import com.shengyecapital.common.constants.ResponseCodeConstants;

/**
 * 数据库中找不到对应的记录。
 */
public class RecordNotFoundException extends CustomException {

    public RecordNotFoundException(String _recordName, String _recordValue) {
        super(String.format("%s(%s) not found", _recordName, _recordValue));
    }

    @Override
    public String getErrorCode() {
        return ResponseCodeConstants.RECORD_NOT_FOUND_ERROR;
    }

}
