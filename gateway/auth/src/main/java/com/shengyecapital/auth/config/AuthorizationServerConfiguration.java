package com.shengyecapital.auth.config;

import com.shengyecapital.auth.ldap.LdapTokenGranter;
import com.shengyecapital.auth.service.CustomUserDetailsService;
import com.shengyecapital.auth.tokenstore.CustomTokenStoreDelegator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 认证服务配置
 *
 * @author tommy.yang
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("customRedisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private ApprovalStore approvalStore;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCodeServices)
                .approvalStore(approvalStore)
                .tokenStore(tokenStore)
                .tokenGranter(tokenGranter(endpoints));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // 允许所有资源服务器访问公钥端点（/oauth/token_key）
                .tokenKeyAccess("permitAll()")
                // 只允许验证用户访问令牌解析端点（/oauth/check_token）
                .checkTokenAccess("isAuthenticated()")
                // 允许客户端发送表单来进行权限认证来获取令牌
                .allowFormAuthenticationForClients();
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public TokenStore jdbcTokenStore() {
        return new CustomTokenStoreDelegator(new JdbcTokenStore(dataSource), redisTemplate);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 添加 OAuth2  tokenGranter
     *
     * @param endpoints
     * @return
     */
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new AuthorizationCodeTokenGranter(endpoints.getTokenServices(), authorizationCodeServices,
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        granters.add(new RefreshTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()));
        granters.add(new ImplicitTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()));
        granters.add(new ClientCredentialsTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()));
        if (authenticationManager != null) {
            granters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(),
                    endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
            granters.add(new LdapTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory()));
        }
        return new CompositeTokenGranter(granters);
    }

}
