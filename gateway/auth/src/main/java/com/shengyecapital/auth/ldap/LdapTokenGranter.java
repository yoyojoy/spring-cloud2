package com.shengyecapital.auth.ldap;

import com.shengyecapital.common.dto.security.CustomUser;
import com.shengyecapital.common.dto.security.LdapUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * oauth2 ldap认证
 *
 * @author long.luo
 * @date 2018/12/20 13:47
 */
public class LdapTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "ldap";

    private final AuthenticationManager authenticationManager;

    public LdapTokenGranter(AuthenticationManager authenticationManager,
                            AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected LdapTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
                               ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String username = parameters.get("username");
        String password = parameters.get("password");
        // Protect from downstream leaks of password
        parameters.remove("password");

        LdapUser ldapUser = new LdapUser(username, password);
        Authentication userAuth = new LdapAuthenticationToken(ldapUser, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
