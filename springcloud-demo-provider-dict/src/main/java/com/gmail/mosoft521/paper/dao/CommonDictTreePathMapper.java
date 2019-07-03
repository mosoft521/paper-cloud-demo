package com.gmail.mosoft521.paper.dao;

import com.gmail.mosoft521.paper.entity.CommonDictTreePath;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathKey;

public interface CommonDictTreePathMapper {
    int deleteByPrimaryKey(CommonDictTreePathKey key);

    int insert(CommonDictTreePath record);

    int insertSelective(CommonDictTreePath record);

    CommonDictTreePath selectByPrimaryKey(CommonDictTreePathKey key);

    int updateByPrimaryKeySelective(CommonDictTreePath record);

    int updateByPrimaryKey(CommonDictTreePath record);
}