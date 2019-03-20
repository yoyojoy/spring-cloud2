package com.shengyecapital.common.client.user;

import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import com.shengyecapital.common.dto.user.AuthorityDto;
import com.shengyecapital.common.dto.user.UserDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public CustomResponseBody<UserDto> loadUserByUserName(String userName) {
                log.error("加载用户数据异常", cause);

                CustomResponseBody<UserDto> responseBody = new CustomResponseBody<>();
                responseBody.setCode(ResponseCodeConstants.REMOTE_CALL_FAILED);
                return responseBody;
            }

            @Override
            public CustomResponseBody<List<AuthorityDto>> loadAuthorityList() {
                log.error("获取权限异常", cause);

                CustomResponseBody<List<AuthorityDto>> responseBody = new CustomResponseBody<>();
                responseBody.setCode(ResponseCodeConstants.REMOTE_CALL_FAILED);
                return responseBody;
            }
        };
    }
}
