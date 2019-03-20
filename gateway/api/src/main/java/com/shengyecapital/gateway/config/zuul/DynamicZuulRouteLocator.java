package com.shengyecapital.gateway.config.zuul;

import com.shengyecapital.gateway.service.GatewayRouteService;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义刷新规则
 * @author long.luo
 * @date 2018/12/13 9:58
 */
public class DynamicZuulRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ZuulProperties properties;

    private GatewayRouteService gatewayRouteService;

    public DynamicZuulRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    void setGatewayRouteService(GatewayRouteService gatewayRouteService) {
        this.gatewayRouteService = gatewayRouteService;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<>();
        routesMap.putAll(super.locateRoutes());
        routesMap.putAll(gatewayRouteService.getProperties());
        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
        routesMap.forEach((key, value) -> {
            String path = key;
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, value);
        });
        return values;
    }
}
