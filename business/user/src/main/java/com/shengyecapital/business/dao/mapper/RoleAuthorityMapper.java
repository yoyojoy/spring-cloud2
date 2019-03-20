package com.shengyecapital.business.dao.mapper;

import com.shengyecapital.business.dao.entity.RoleAuthority;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleAuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAuthority record);

    int insertSelective(RoleAuthority record);

    RoleAuthority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAuthority record);

    int updateByPrimaryKey(RoleAuthority record);
}