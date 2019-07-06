package com.gmail.mosoft521.paper.dao;

import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.entity.CommonDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommonDictMapper {
    long countByExample(CommonDictExample example);

    int deleteByExample(CommonDictExample example);

    int deleteByPrimaryKey(Long dictId);

    int insert(CommonDict record);

    int insertSelective(CommonDict record);

    List<CommonDict> selectByExample(CommonDictExample example);

    CommonDict selectByPrimaryKey(Long dictId);

    int updateByExampleSelective(@Param("record") CommonDict record, @Param("example") CommonDictExample example);

    int updateByExample(@Param("record") CommonDict record, @Param("example") CommonDictExample example);

    int updateByPrimaryKeySelective(CommonDict record);

    int updateByPrimaryKey(CommonDict record);
}