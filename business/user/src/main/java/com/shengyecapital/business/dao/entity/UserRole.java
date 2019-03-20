package com.shengyecapital.business.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 用户角色关联表
 * t_user_role
 * @author tommy.yang
 * @date 2018-12-13T10:12:45.159+08:00
 */
@Data
public class UserRole {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 角色编号
     */
    private Integer roleId;

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