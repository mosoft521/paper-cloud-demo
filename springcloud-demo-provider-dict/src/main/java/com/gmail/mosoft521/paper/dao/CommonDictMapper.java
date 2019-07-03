package com.gmail.mosoft521.paper.dao;

import com.gmail.mosoft521.paper.entity.CommonDict;

public interface CommonDictMapper {
    int deleteByPrimaryKey(Long dictId);

    int insert(CommonDict record);

    int insertSelective(CommonDict record);

    CommonDict selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(CommonDict record);

    int updateByPrimaryKey(CommonDict record);
}