package com.shengyecapital.auth.service.impl;

import com.shengyecapital.auth.service.LdapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * ldap 认证实现
 *
 * @author long.luo
 */
@Slf4j
@Service
public class LdapServiceImpl implements LdapService {


    @Value("${spring.ldap.defaultDC}")
    private String dc;

    @Value("${spring.ldap.urls}")
    private String providerUrl;

    @Override
    public boolean authenticate(String username, String password) {
        return this.getLdapContext(username, password) != null;
    }

    private LdapContext getLdapContext(String username, String password) {
        return this.getLdapContext(username, password, dc);
    }

    private LdapContext getLdapContext(String username, String password, String dc) {
        Hashtable<String, String> env = new Hashtable<>();
        LdapContext ldapContext = null;
        //用户名称，cn,ou,dc 分别：用户，组，域
        env.put(Context.SECURITY_PRINCIPAL, username + "@" + dc);
        //用户密码 cn 的密码
        env.put(Context.SECURITY_CREDENTIALS, password);
        //url 格式：协议://ip:端口/组,域   ,直接连接到域或者组上面
        env.put(Context.PROVIDER_URL, providerUrl);
        //LDAP 工厂
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        //验证的类型     "none", "simple", "strong"
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        try {
            ldapContext = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            log.error("域账户验证失败, 原因: {}", e);
        }
        return ldapContext;
    }

}
