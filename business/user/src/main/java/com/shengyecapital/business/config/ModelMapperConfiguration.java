package com.shengyecapital.business.config;

import com.shengyecapital.business.dao.entity.Authority;
import com.shengyecapital.business.dao.entity.User;
import com.shengyecapital.common.dto.user.AuthorityDto;
import com.shengyecapital.common.dto.user.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(User.class, UserDto.class).setConverter(context -> {
            User source = context.getSource();
            UserDto destination = new UserDto();

            destination.setId(source.getId());
            destination.setUserName(source.getUserName());
            destination.setPassword(source.getPassword());
            destination.setNickName(source.getNickName());
            destination.setEmail(source.getEmail());
            destination.setMobileNumber(source.getMobileNumber());

            return destination;
        });

        modelMapper.createTypeMap(Authority.class, AuthorityDto.class).setConverter(context -> {
            Authority source = context.getSource();
            AuthorityDto destination = new AuthorityDto();

            destination.setId(source.getId());
            destination.setAuthorityName(source.getAuthorityName());
            destination.setDescription(source.getDescription());

            return destination;
        });

        return modelMapper;
    }

}
