package com.shengyecapital.auth.ldap;

import com.shengyecapital.auth.service.LdapService;
import com.shengyecapital.common.client.user.UserClient;
import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import com.shengyecapital.common.dto.security.LdapUser;
import com.shengyecapital.common.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ldap 认证提供者
 *
 * @author long.luo
 * @date 2018/12/19 9:35
 */
@Component("ldapAuthenticationProvider")
public class LdapAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LdapService ldapService;

    @Autowired
    private UserClient userClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LdapAuthenticationToken token = (LdapAuthenticationToken) authentication;
        LdapUser ldapUser = (LdapUser) token.getPrincipal();
        String password = (String) token.getCredentials();

        // ldap 认证
        boolean isPass = ldapService.authenticate(ldapUser.getUsername(), password);
        if (!isPass) {
            throw new BadCredentialsException("Bad credentials");
        }

        UserDto userDto = this.getUserDto(ldapUser.getUsername());
        // 处理 customerUser
        this.handleLdapUser(ldapUser, userDto);
        // 后台读取权限
        List<GrantedAuthority> authorities = getGrantedAuthorities(userDto);

        return new LdapAuthenticationToken(ldapUser, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LdapAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 后台读取权限
     *
     * @param userDto userDto
     * @return List<GrantedAuthority>
     */
    private List<GrantedAuthority> getGrantedAuthorities(UserDto userDto) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDto.getAuthorities().forEach(
                authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()))
        );
        return authorities;
    }

    /**
     * 获取 userDto
     *
     * @param userName userName
     * @return
     */
    private UserDto getUserDto(String userName) {
        CustomResponseBody<UserDto> responseBody = userClient.loadUserByUserName(userName);
        if (!responseBody.getCode().equals(ResponseCodeConstants.OK)) {
            throw new BadCredentialsException("Bad credentials");
        }
        return responseBody.getData();
    }

    /**
     * 处理 customerUser
     *
     * @param ldapUser ldapUser
     * @param userDto userDto
     */
    private void handleLdapUser(LdapUser ldapUser, UserDto userDto) {
        ldapUser.setId(userDto.getId());
        ldapUser.setNickName(userDto.getNickName());
    }
}
