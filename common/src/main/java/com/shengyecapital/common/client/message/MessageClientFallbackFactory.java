package com.shengyecapital.common.client.message;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageClientFallbackFactory implements FallbackFactory<MessageClient> {

    @Override
    public MessageClient create(Throwable throwable) {
        return new MessageClient() {
            @Override
            public String send(String content) {
                log.error(throwable.getMessage());

                return "发送失败";
            }
        };
    }

}
