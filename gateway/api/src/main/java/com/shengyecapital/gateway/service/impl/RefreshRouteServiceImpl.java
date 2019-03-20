package com.shengyecapital.gateway.service.impl;

import com.shengyecapital.gateway.service.RefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 刷新路由实现
 *
 * @author long.luo
 * @date 2018/12/13 15:19
 */
@Service
public class RefreshRouteServiceImpl implements RefreshRouteService {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteLocator routeLocator;

    @Override
    public void routeRefresh() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }
}
