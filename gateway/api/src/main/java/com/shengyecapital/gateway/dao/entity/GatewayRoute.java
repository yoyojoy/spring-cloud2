package com.shengyecapital.gateway.dao.entity;

import java.util.Date;
import lombok.Data;

/**
 * 网关路由表
 * gateway_route
 * @author long.luo
 * @date 2018-12-13T10:18:46.739+08:00
 */
@Data
public class GatewayRoute {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 路由编号
     */
    private String routeId;

    /**
     * 服务编号
     */
    private String serviceId;

    /**
     * 路由的路径
     */
    private String path;

    /**
     * 映射到路由的完整物理路径
     */
    private String url;

    /**
     * 路由前缀标志(0:不处理 1:去掉)
     */
    private Byte stripPrefix;

    /**
     * 重试标志(0:不重试 1:重试)
     */
    private Byte retryable;

    /**
     * 启用标志(0:不启用 1:启用)
     */
    private Byte enabled;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;
}