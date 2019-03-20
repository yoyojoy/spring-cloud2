package com.shengyecapital.common.client.user;

import com.shengyecapital.common.dto.common.CustomResponseBody;
import com.shengyecapital.common.dto.user.AuthorityDto;
import com.shengyecapital.common.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @RequestMapping(value = "/loading", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CustomResponseBody<UserDto> loadUserByUserName(@RequestParam("user_name") String userName);

    /**
     * 获取权限列表
     *
     * @return List<AuthorityDto>
     */
    @RequestMapping(value = "/authority/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CustomResponseBody<List<AuthorityDto>> loadAuthorityList();

}
