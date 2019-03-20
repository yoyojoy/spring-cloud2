package com.shengyecapital.business.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 角色表
 * t_role
 * @author tommy.yang
 * @date 2018-12-26T11:04:21.450+08:00
 */
@Data
public class Role {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 系统编码(前两位系统，后两位场景）
     */
    private String systemCode;

    /**
     * 父编号
     */
    private Integer parentId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String description;

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