package com.shengyecapital.gateway.config;

import com.shengyecapital.common.client.user.UserClient;
import com.shengyecapital.gateway.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private UserClient userClient;

    @Autowired
    @Qualifier("customRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(customAuthenticationEntryPoint);
        resources.accessDeniedHandler(accessDeniedHandler);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setSecurityMetadataSource(mySecurityMetadataSource(fsi.getSecurityMetadataSource()));
                        return fsi;
                    }
                })
                .antMatchers("/uaa/oauth/token", "/actuator/health", "/actuator/info", "/actuator", "/route/refresh").permitAll()
                .anyRequest().authenticated();
    }

    public AppFilterInvocationSecurityMetadataSource mySecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        AppFilterInvocationSecurityMetadataSource appFilterInvocationSecurityMetadataSource = new AppFilterInvocationSecurityMetadataSource();
        appFilterInvocationSecurityMetadataSource.setFilterInvocationSecurityMetadataSource(filterInvocationSecurityMetadataSource);
        appFilterInvocationSecurityMetadataSource.setRedisTemplate(redisTemplate);
        appFilterInvocationSecurityMetadataSource.setUserClient(userClient);
        return appFilterInvocationSecurityMetadataSource;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(securityExpressionHandler());

        List<AccessDecisionVoter<?>> decisionVoters
                = Arrays.asList(
                webExpressionVoter,
                new RoleVoter());
        return new AffirmativeBased(decisionVoters);
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler securityExpressionHandler() {
        return new OAuth2WebSecurityExpressionHandler();
    }
}

