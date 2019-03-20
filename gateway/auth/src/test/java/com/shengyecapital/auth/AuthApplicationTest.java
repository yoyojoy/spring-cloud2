package com.shengyecapital.auth;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthApplicationTest {

    @Test
    public void encoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("123456");
        System.out.println(hashedPassword);
    }

}