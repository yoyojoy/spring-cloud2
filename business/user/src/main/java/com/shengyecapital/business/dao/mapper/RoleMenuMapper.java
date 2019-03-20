package com.shengyecapital.business.dao.mapper;

import com.shengyecapital.business.dao.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);
}