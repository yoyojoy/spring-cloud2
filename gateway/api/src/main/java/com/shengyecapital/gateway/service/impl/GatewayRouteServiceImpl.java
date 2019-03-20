package com.shengyecapital.gateway.service.impl;

import com.shengyecapital.gateway.dao.entity.GatewayRoute;
import com.shengyecapital.gateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态路由策略
 *
 * @author long.luo
 * @date 2018/12/13 10:23
 */
@Service
public class GatewayRouteServiceImpl implements GatewayRouteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL = "SELECT * FROM gateway_route WHERE enabled = 1";

    @Override
    public Map<String, ZuulRoute> getProperties() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();
        List<GatewayRoute> gatewayRouteList = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(GatewayRoute.class));
        gatewayRouteList.forEach(entity -> {
            if (StringUtils.isEmpty(entity.getPath())) {
                return;
            }
            ZuulRoute zuulRoute = getZuulRoute(entity);
            routes.put(zuulRoute.getPath(), zuulRoute);
        });
        return routes;
    }

    /**
     * 获取 ZuulRoute
     * @param entity entity
     * @return
     */
    private ZuulRoute getZuulRoute(GatewayRoute entity) {
        ZuulRoute zuulRoute = new ZuulRoute();
        zuulRoute.setId(entity.getRouteId());
        zuulRoute.setPath(entity.getPath());
        zuulRoute.setServiceId(entity.getServiceId());
        zuulRoute.setUrl(entity.getUrl());
        zuulRoute.setStripPrefix(entity.getStripPrefix() != 1);
        zuulRoute.setRetryable(entity.getRetryable() == 1);
        return zuulRoute;
    }
}
