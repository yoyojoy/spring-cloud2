package com.shengyecapital.gateway.controller;

import com.shengyecapital.gateway.service.RefreshRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 刷新接口
 *
 * @author long.luo
 * @date 2018/12/13 15:22
 */
@RestController
@Slf4j
public class RefreshController {

    @Autowired
    RefreshRouteService refreshRouteService;

    @GetMapping("/route/refresh")
    public String refresh() {
        refreshRouteService.routeRefresh();
        return "refresh success";
    }
}
