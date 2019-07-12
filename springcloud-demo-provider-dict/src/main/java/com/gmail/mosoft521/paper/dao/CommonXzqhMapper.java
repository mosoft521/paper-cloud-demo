package com.gmail.mosoft521.paper.dao;

import com.gmail.mosoft521.paper.entity.CommonXzqh;
import com.gmail.mosoft521.paper.entity.CommonXzqhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommonXzqhMapper {
    long countByExample(CommonXzqhExample example);

    int deleteByExample(CommonXzqhExample example);

    int deleteByPrimaryKey(Long dictId);

    int insert(CommonXzqh record);

    int insertSelective(CommonXzqh record);

    List<CommonXzqh> selectByExample(CommonXzqhExample example);

    CommonXzqh selectByPrimaryKey(Long dictId);

    int updateByExampleSelective(@Param("record") CommonXzqh record, @Param("example") CommonXzqhExample example);

    int updateByExample(@Param("record") CommonXzqh record, @Param("example") CommonXzqhExample example);

    int updateByPrimaryKeySelective(CommonXzqh record);

    int updateByPrimaryKey(CommonXzqh record);
}