package com.shengyecapital.common.dto.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

/**
 * ldap用户身份凭证
 *
 * @author long.luo
 * @date 2019/1/2 13:50
 */
@Data
public class LdapUser implements Serializable {

    private Integer id;
    private String nickName;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

    public LdapUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
