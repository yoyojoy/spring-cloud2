package com.shengyecapital.business.dao.mapper;

import com.shengyecapital.business.dao.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}