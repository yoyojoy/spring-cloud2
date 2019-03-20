package com.shengyecapital.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author tommy.yang
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public Authentication user() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
