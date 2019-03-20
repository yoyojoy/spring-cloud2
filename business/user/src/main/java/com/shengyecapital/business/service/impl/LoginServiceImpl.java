package com.shengyecapital.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.shengyecapital.business.dao.entity.User;
import com.shengyecapital.business.dao.mapper.UserMapper;
import com.shengyecapital.business.dto.LoginAo;
import com.shengyecapital.business.dto.LoginVo;
import com.shengyecapital.business.service.LoginService;
import com.shengyecapital.common.client.auth.AuthClient;
import com.shengyecapital.common.exception.AuthenticationErrorException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录实现服务
 *
 * @author long.luo
 * @date 2018/12/27 13:58
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final String ACCESS_TOKEN = "access_token";

    private static final String TOKEN_TYPE = "token_type";

    private static final String REFRESH_TOKEN = "refresh_token";

    private static final String EXPIRES_IN = "expires_in";

    private static final String SCOPE = "scope";

    private static final String CODE = "code";

    private static final String MESSAGE = "message";

    private static final String ENVIRONMENT_PROD = "prod";

    private static final String CLIENT_ID = "api";

    private static final String CLIENT_SECRET = "123456";

    private static final String GRANT_TYPE = "ldap";

    private static final String AUTH_SCOPE = "read";

    private static final String LOGIN_ERROR = "login:error:";

    private static final int PASSWORD_MAX_ERROR_TIMES = 5;

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    @Qualifier("customRedisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthClient authClient;

    @Override
    public LoginVo login(LoginAo loginAo) {
        // 登录前参数校验
        this.preCheckParams(loginAo);

        // 校验验证码
        this.verifyCaptchaCode(loginAo.getCaptchaCode(), loginAo.getCaptchaKey());

        // 校验密码错误次数
        this.verifyPasswordErrorTimes(loginAo.getUsername());

        // 远程认证
        Map resultMap = remoteAuthentication(loginAo);

        // 认证服务器异常返回处理
        this.remoteCallFailed(resultMap, loginAo.getUsername());

        // 认证服务器正常返回处理
        LoginVo loginVo = this.remoteCallSucceeded(resultMap);

        // 更新最后一次登录时间
        this.updateLastLoginTime(loginAo.getUsername());

        return loginVo;
    }

    /**
     * 登录前参数校验
     *
     * @param loginAo loginAo
     */
    private void preCheckParams(@NonNull LoginAo loginAo) {
        if (StringUtils.isBlank(loginAo.getUsername())) {
            throw new AuthenticationErrorException("用户名不能为空");
        }
        if (StringUtils.isBlank(loginAo.getPassword())) {
            throw new AuthenticationErrorException("密码不能为空");
        }
        if (StringUtils.isBlank(loginAo.getCaptchaCode())) {
            throw new AuthenticationErrorException("验证码不能为空");
        }
        if (StringUtils.isBlank(loginAo.getCaptchaKey())) {
            throw new AuthenticationErrorException("验证码key不能为空");
        }
    }

    /**
     * 校验验证码
     *
     * @param captchaCode 验证码
     * @param captchaKey  验证码缓存存储key
     */
    private void verifyCaptchaCode(String captchaCode, String captchaKey) {
        if (ENVIRONMENT_PROD.equals(env)) {
            Object code = redisTemplate.boundValueOps("captchaKey:" + captchaKey).get();
            if (code == null) {
                throw new AuthenticationErrorException("验证码已过期");
            }
            if (!captchaCode.equalsIgnoreCase(code.toString())) {
                throw new AuthenticationErrorException("验证码错误");
            }
        }
    }

    /**
     * 校验密码错误次数
     *
     * @param username username
     */
    private void verifyPasswordErrorTimes(String username) {
        Object value = redisTemplate.opsForValue().get(LOGIN_ERROR + username);
        if (value != null) {
            if (Integer.parseInt(value.toString()) >= PASSWORD_MAX_ERROR_TIMES) {
                throw new AuthenticationErrorException("您输入的密码错误5次,已被锁定2小时");
            }
        }
    }

    /**
     * 远程认证
     *
     * @param loginAo loginAo
     * @return Map
     */
    private Map remoteAuthentication(LoginAo loginAo) {
        String customResponseBody = authClient.authentication(GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, AUTH_SCOPE, loginAo.getUsername(), loginAo.getPassword());
        return JSON.parseObject(customResponseBody, Map.class);
    }

    /**
     * 认证服务器异常结果返回处理
     *
     * @param resultMap resultMap
     */
    private void remoteCallFailed(Map resultMap, String username) {
        if (resultMap == null) {
            throw new AuthenticationErrorException("认证结果返回空，认证失败");
        }
        if (resultMap.containsKey(CODE)) {
            String message = resultMap.get(MESSAGE).toString();
            // 认证身份异常
            String invalidGrant = "invalid_grant";
            if (message.contains(invalidGrant)) {
                this.handleInvalidGrant(username);
            } else {
                log.info("认证系统返回的错误编码:{}", resultMap.get(CODE));
                log.info("认证系统返回的错误信息:{}", resultMap.get(MESSAGE).toString());
                throw new AuthenticationErrorException("认证服务器异常,请联系管理员");
            }
        }
        redisTemplate.delete(LOGIN_ERROR + username);
    }

    /**
     * 处理 InvalidGrant 异常
     *
     * @param username 用户
     */
    private void handleInvalidGrant(String username) {
        User user = userMapper.findByUserName(username);
        if (user == null) {
            throw new AuthenticationErrorException("您输入的用户名系统不存在");
        }
        AtomicInteger errorNum = new AtomicInteger(0);
        Object value = redisTemplate.opsForValue().get(LOGIN_ERROR + username);
        if (value != null) {
            errorNum = new AtomicInteger(Integer.parseInt(value.toString()));
        }
        if (errorNum.get() >= PASSWORD_MAX_ERROR_TIMES) {
            throw new AuthenticationErrorException("您输入的密码错误5次,已被锁定2小时");
        }
        redisTemplate.boundValueOps(LOGIN_ERROR + username).set(errorNum.incrementAndGet(), 2, TimeUnit.HOURS);
        throw new AuthenticationErrorException("您输入的密码错误" + errorNum.get() + "次");
    }

    /**
     * 认证服务器正常结果返回处理
     *
     * @param resultMap resultMap
     * @return LoginVo
     */
    private LoginVo remoteCallSucceeded(Map resultMap) {
        LoginVo loginVo = new LoginVo();
        this.handleLoginVo(resultMap, loginVo);
        return loginVo;
    }

    /**
     * 更新用户最后一次登录时间
     *
     * @param username
     */
    private void updateLastLoginTime(String username) {
        User user = userMapper.findByUserName(username);
        User update = new User();
        update.setId(user.getId());
        update.setLastLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(update);
    }

    /**
     * 处理 resultMap - loginVo
     *
     * @param resultMap resultMap
     * @param loginVo   loginVo
     */
    private void handleLoginVo(Map resultMap, LoginVo loginVo) {
        if (resultMap.get(ACCESS_TOKEN) != null) {
            loginVo.setAccessToken(resultMap.get(ACCESS_TOKEN).toString());
        }
        if (resultMap.get(TOKEN_TYPE) != null) {
            loginVo.setTokenType(resultMap.get(TOKEN_TYPE).toString());
        }
        if (resultMap.get(REFRESH_TOKEN) != null) {
            loginVo.setRefreshToken(resultMap.get(REFRESH_TOKEN).toString());
        }
        if (resultMap.get(EXPIRES_IN) != null) {
            loginVo.setExpiresIn((Integer) resultMap.get(EXPIRES_IN));
        }
        if (resultMap.get(SCOPE) != null) {
            loginVo.setScope(resultMap.get(SCOPE).toString());
        }
    }
}
