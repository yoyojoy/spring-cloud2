package com.shengyecapital.business.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 角色权限关联表
 * t_role_authority
 * @author tommy.yang
 * @date 2018-12-13T10:12:45.162+08:00
 */
@Data
public class RoleAuthority {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色编号
     */
    private Integer roleId;

    /**
     * 权限编号
     */
    private Integer authorityId;

    /**
     * 创建者
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改者
     */
    private Integer updatedBy;

    /**
     * 修改时间
     */
    private Date updatedAt;
}