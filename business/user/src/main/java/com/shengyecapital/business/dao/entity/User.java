package com.shengyecapital.business.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * t_user
 * @author tommy.yang
 * @date 2018-12-13T10:12:45.079+08:00
 */
@Data
public class User {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名(登录名)
     */
    private String userName;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobileNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否域账户(0:否 1:是)
     */
    private Byte isDomainAccount;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 启用状态(0:未启用 1:已启用)
     */
    private Byte enabled;

    /**
     * 创建者
     */
    private Integer createdBy;

    /**
     * 修改者
     */
    private Integer updatedBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;
}