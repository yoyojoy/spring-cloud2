package com.shengyecapital.business.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 菜单表
 * t_menu
 * @author tommy.yang
 * @date 2018-12-26T11:04:21.454+08:00
 */
@Data
public class Menu {
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
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单链接
     */
    private String menuLink;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 启用状态(0:未启用 1:已启用)
     */
    private Byte enabled;

    /**
     * 排序号(数字越小排名越靠前)
     */
    private Integer orderNum;

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