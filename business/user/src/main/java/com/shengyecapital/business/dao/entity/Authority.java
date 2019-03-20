package com.shengyecapital.business.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 权限表
 * t_authority
 * @author tommy.yang
 * @date 2018-12-13T10:12:45.160+08:00
 */
@Data
public class Authority {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 系统编码(前两位系统，后两位场景）
     */
    private String systemCode;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 排序号(数字越小排名越靠前)
     */
    private Integer orderNum;

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