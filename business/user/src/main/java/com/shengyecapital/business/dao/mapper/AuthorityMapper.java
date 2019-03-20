package com.shengyecapital.business.dao.mapper;

import com.shengyecapital.business.dao.entity.Authority;
import com.shengyecapital.common.dto.user.AuthorityDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);

    List<Authority> findByUserId(@Param("userId") Integer userId);

    List<AuthorityDto> loadAuthorityList();
}