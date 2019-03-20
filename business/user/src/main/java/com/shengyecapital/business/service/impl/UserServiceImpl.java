package com.shengyecapital.business.service.impl;

import com.shengyecapital.business.dao.entity.Authority;
import com.shengyecapital.business.dao.entity.User;
import com.shengyecapital.business.dao.mapper.AuthorityMapper;
import com.shengyecapital.business.dao.mapper.UserMapper;
import com.shengyecapital.business.service.UserService;
import com.shengyecapital.common.dto.user.AuthorityDto;
import com.shengyecapital.common.dto.user.UserDto;
import com.shengyecapital.common.exception.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto findUserByUserName(String userName) {
        User user = Optional.ofNullable(userMapper.findByUserName(userName))
                .orElseThrow(() -> new RecordNotFoundException("user", userName));

        UserDto userDto = modelMapper.map(user, UserDto.class);

        final List<Authority> authorities = authorityMapper.findByUserId(user.getId());
        ArrayList<AuthorityDto> authorityDtos = new ArrayList<>();
        authorities.forEach(authority -> {
            AuthorityDto authorityDto = modelMapper.map(authority, AuthorityDto.class);
            authorityDtos.add(authorityDto);
        });
        userDto.setAuthorities(authorityDtos);
        return userDto;
    }

    @Override
    public List<AuthorityDto> loadAuthorityList() {
        return authorityMapper.loadAuthorityList();
    }

}
