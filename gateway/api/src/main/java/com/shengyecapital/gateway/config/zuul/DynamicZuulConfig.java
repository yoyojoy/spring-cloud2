package com.shengyecapital.gateway.config.zuul;

import com.shengyecapital.gateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由配置
 * @author long.luo
 * @date 2018/12/13 10:10
 */
@Configuration
public class DynamicZuulConfig {

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private DispatcherServletRegistrationBean dispatcherServletRegistrationBean;

    @Autowired
    private GatewayRouteService gatewayRouteService;

    @Bean
    public DynamicZuulRouteLocator routeLocator() {
        DynamicZuulRouteLocator dynamicZuulRouteLocator = new DynamicZuulRouteLocator(dispatcherServletRegistrationBean.getPrefix(), zuulProperties);
        dynamicZuulRouteLocator.setGatewayRouteService(gatewayRouteService);
        return dynamicZuulRouteLocator;
    }
}
