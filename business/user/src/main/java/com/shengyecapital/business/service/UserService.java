package com.shengyecapital.business.service;

import com.shengyecapital.common.dto.user.AuthorityDto;
import com.shengyecapital.common.dto.user.UserDto;

import java.util.List;

public interface UserService {

    /**
     * 根据登录名查找用户
     *
     * @param userName
     * @return
     */
    UserDto findUserByUserName(String userName);

    /**
     * 获取权限用户
     * @return List<AuthorityDto>
     */
    List<AuthorityDto> loadAuthorityList();
}
