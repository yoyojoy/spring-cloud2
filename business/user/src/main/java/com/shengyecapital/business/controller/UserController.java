package com.shengyecapital.business.controller;

import com.shengyecapital.business.service.UserService;
import com.shengyecapital.common.constants.RequestHeaderConstants;
import com.shengyecapital.common.context.CustomHystrixContext;
import com.shengyecapital.common.dto.user.AuthorityDto;
import com.shengyecapital.common.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/")
    public void index(@RequestHeader(RequestHeaderConstants.X_USER_ID) Integer userId) {
        if (CustomHystrixContext.getInstance().getUserId().equals(userId)) {
            log.info("hello," + userId);
        }
    }

    @GetMapping(value = "/loading")
    public UserDto loadUserByUserName(@RequestParam("user_name") String userName) {
        return userService.findUserByUserName(userName);
    }

    @GetMapping(value = "/authority/list")
    public List<AuthorityDto> loadAuthorityList() {
        return userService.loadAuthorityList();
    }
}
