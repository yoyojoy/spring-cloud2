package com.shengyecapital.business.service;

import com.shengyecapital.business.dto.LoginAo;
import com.shengyecapital.business.dto.LoginVo;

/**
 * 登录 service
 *
 * @author long.luo
 * @date 2018/12/27 13:56
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param loginAo loginAo
     * @return LoginVo
     */
    LoginVo login(LoginAo loginAo);
}
