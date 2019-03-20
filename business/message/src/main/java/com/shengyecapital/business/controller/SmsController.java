package com.shengyecapital.business.controller;

import com.shengyecapital.common.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SmsController {

    @PostMapping("/sms/send")
    public String send(@RequestBody String content) {
        log.info("发送短信：" + content);
        return "发送成功";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/apply")
    public UserDto apply() {
        return new UserDto();
    }

    @GetMapping("/apply1")
    public void apply1() {

    }

    @GetMapping("/apply2")
    public String apply2() {
        return "test";
    }
}
