package com.shengyecapital.business.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 角色菜单关联表
 * t_role_menu
 * @author tommy.yang
 * @date 2018-12-13T10:12:45.165+08:00
 */
@Data
public class RoleMenu {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色编号
     */
    private Integer roleId;

    /**
     * 菜单编号
     */
    private Integer menuId;

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