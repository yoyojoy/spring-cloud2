package com.shengyecapital.common.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AuthorityDto implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * Apache Ant路径匹配规则
     */
    private String antPattern;

    /**
     * 权限描述
     */
    private String description;

    /**
     * Apache Ant路径匹配规则
     */
    private String orderNum;
}
