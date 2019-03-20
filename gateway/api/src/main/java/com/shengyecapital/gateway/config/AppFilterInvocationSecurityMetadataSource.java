package com.shengyecapital.gateway.config;

import com.shengyecapital.common.client.user.UserClient;
import com.shengyecapital.common.constants.RedisKeyConstants;
import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import com.shengyecapital.common.dto.user.AuthorityDto;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自定义 FilterInvocationSecurityMetadataSource
 *
 * @author long.luo
 */
public class AppFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    private UserClient userClient;

    private RedisTemplate redisTemplate;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public void setFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
    }

    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static List<String> permitUrls = Arrays.asList("/actuator/health/**", "/*/permit/**");

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = this.getUrl(fi);

        // 无需权限地址处理
        for (String permitUrl : permitUrls) {
            if (antPathMatcher.match(permitUrl, url)) {
                return null;
            }
        }
        List<AuthorityDto> authorityDtoList = getAuthorityList();
        for (AuthorityDto authority : authorityDtoList) {
            if (antPathMatcher.match(authority.getAntPattern(), url)) {
                return SecurityConfig.createList(authority.getAuthorityName());
            }
        }
        return filterInvocationSecurityMetadataSource.getAttributes(object);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * 获取权限（缓存redis中）
     *
     * @return List<AuthorityDto>
     */
    private List<AuthorityDto> getAuthorityList() {
        List<AuthorityDto> authorityDtoList;
        Object obj = redisTemplate.opsForValue().get(RedisKeyConstants.AUTHORITY_KEY);
        if (obj == null) {
            authorityDtoList = this.getUserAuthorityList();
            BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(RedisKeyConstants.AUTHORITY_KEY);
            boundValueOperations.setIfAbsent(authorityDtoList);
            boundValueOperations.expire(2, TimeUnit.HOURS);
        } else {
            authorityDtoList = (List<AuthorityDto>) obj;
        }
        return authorityDtoList;
    }

    /**
     * 读取user服务所有权限
     *
     * @return List<AuthorityDto>
     */
    private List<AuthorityDto> getUserAuthorityList() {
        CustomResponseBody<List<AuthorityDto>> responseBody = userClient.loadAuthorityList();
        if (!responseBody.getCode().equals(ResponseCodeConstants.OK)) {
            throw new BadCredentialsException("Bad credentials");
        }
        return responseBody.getData();
    }

    /**
     * 获取GET请求去参数后的url
     *
     * @return
     */
    private String getUrl(FilterInvocation fi) {
        String url = fi.getRequestUrl();
        String expr = "?";
        if (url.contains(expr)) {
            url = url.substring(0, url.indexOf(expr));
        }
        return url;
    }
}
