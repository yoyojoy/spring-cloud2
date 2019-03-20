package com.shengyecapital.auth.service;

import com.shengyecapital.common.client.user.UserClient;
import com.shengyecapital.common.constants.ResponseCodeConstants;
import com.shengyecapital.common.dto.common.CustomResponseBody;
import com.shengyecapital.common.dto.security.CustomUser;
import com.shengyecapital.common.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        CustomResponseBody<UserDto> responseBody = userClient.loadUserByUserName(userName);
        if (!responseBody.getCode().equals(ResponseCodeConstants.OK)) {
            throw new UsernameNotFoundException(userName);
        }

        UserDto userDto = responseBody.getData();

        List<GrantedAuthority> authorities = new ArrayList<>();
        userDto.getAuthorities().forEach(
                authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()))
        );

        CustomUser customUser = new CustomUser(
                userDto.getId(),
                userDto.getUserName(),
                userDto.getNickName(),
                userDto.getPassword(),
                true,
                true,
                true,
                true,
                authorities);

        return customUser;
    }
}
