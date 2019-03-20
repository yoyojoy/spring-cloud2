package com.shengyecapital.common.client.message;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "message", fallbackFactory = MessageClientFallbackFactory.class)
public interface MessageClient {

    @RequestMapping(value = "/sms/send", method = RequestMethod.POST)
    String send(@RequestBody String content);

}
