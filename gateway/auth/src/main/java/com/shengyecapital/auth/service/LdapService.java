package com.shengyecapital.auth.service;

/**
 * ldap 相关服务
 *
 * @author long.luo
 */
public interface LdapService {

    /**
     * 验证用户在域账户服务器中的合法性
     *
     * @param userName userName
     * @param password password
     * @return
     */
    boolean authenticate(String userName, String password);
}
