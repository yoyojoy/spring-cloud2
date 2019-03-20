package com.shengyecapital.business.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.shengyecapital.business.dto.LoginAo;
import com.shengyecapital.business.dto.LoginVo;
import com.shengyecapital.business.service.LoginService;
import com.shengyecapital.common.exception.ServerErrorException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录Controller
 *
 * @author long.luo
 * @date 2018/12/26 15:02
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    @Qualifier("customRedisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "获取验证码", notes = "查验证码")
    @PostMapping("/permit/imgCode")
    public Map<String, String> getCaptcha(HttpServletResponse response) throws Exception {
        String capText = defaultKaptcha.createText();
        Map<String, String> map = new HashMap<>(2);
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            redisTemplate.boundValueOps("captchaKey:" + uuid).set(capText, 60 * 10, TimeUnit.SECONDS);
            map.put("captchaKey", uuid);
            Cookie cookie = new Cookie("captchaCode", uuid);
            response.addCookie(cookie);
        } catch (Exception e) {
            log.info("获取验证码失败", e);
            throw new ServerErrorException("获取验证码失败");
        }
        BufferedImage bi = defaultKaptcha.createImage(capText);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", out);
        Base64 encoder = new Base64();
        map.put("img", "data:image/jpeg;base64," + encoder.encodeToString(out.toByteArray()));
        return map;
    }

    @ApiOperation(value = "统一认证授权接口", notes = "统一认证授权接口")
    @PostMapping("/permit/login")
    public LoginVo login(@Valid LoginAo loginAo) {
        return loginService.login(loginAo);
    }
}
