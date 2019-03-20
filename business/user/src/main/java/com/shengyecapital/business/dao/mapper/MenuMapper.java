package com.shengyecapital.business.dao.mapper;

import com.shengyecapital.business.dao.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}