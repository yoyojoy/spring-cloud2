package com.shengyecapital.gateway.service;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import java.util.Map;

/**
 * 动态路由策略
 *
 * @author long.luo
 * @date 2018/12/13 10:23
 */
public interface GatewayRouteService {

    /**
     * 获取路由配置
     *
     * @return map
     */
    Map<String, ZuulRoute> getProperties();
}
