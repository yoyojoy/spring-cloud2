package com.shengyecapital.common.constants;

/**
 * 执行结果常量
 */
public interface ResponseCodeConstants {

    String OK = "200";

    /**
     * 未认证
     */
    String UNAUTHORIZED = "401";
    /**
     * 没有权限
     */
    String UNPERMISSION = "403";

    String REMOTE_CALL_FAILED = "500";

    String BAD_REQUEST = "400";

    String INTERNAL_ERROR = "100";

    String DATABASE_ERROR = "997";

    /**
     * 记录未找到
     */
    String RECORD_NOT_FOUND_ERROR = "998";
    /**
     * 系统服务异常
     */
    String SERVER_ERROR = "999";

}
