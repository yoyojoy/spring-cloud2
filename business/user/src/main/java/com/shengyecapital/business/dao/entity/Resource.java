package com.shengyecapital.business.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 权限资源表
 * t_resource
 * @author tommy.yang
 * @date 2018-12-13T10:12:45.166+08:00
 */
@Data
public class Resource {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限编号
     */
    private Integer authorityId;

    /**
     * 请求方法(0:所有 1:GET 2:POST 3:PUT 4:DELET 5:HEAD 6:OPTIONS)
     */
    private Byte httpMethod;

    /**
     * Apache Ant路径匹配规则
     */
    private String antPattern;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;
}