package com.gmail.mosoft521.paper.dao;

import com.gmail.mosoft521.paper.entity.CommonDictTreePath;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathExample;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommonDictTreePathMapper {
    long countByExample(CommonDictTreePathExample example);

    int deleteByExample(CommonDictTreePathExample example);

    int deleteByPrimaryKey(CommonDictTreePathKey key);

    int insert(CommonDictTreePath record);

    int insertSelective(CommonDictTreePath record);

    List<CommonDictTreePath> selectByExample(CommonDictTreePathExample example);

    CommonDictTreePath selectByPrimaryKey(CommonDictTreePathKey key);

    int updateByExampleSelective(@Param("record") CommonDictTreePath record, @Param("example") CommonDictTreePathExample example);

    int updateByExample(@Param("record") CommonDictTreePath record, @Param("example") CommonDictTreePathExample example);

    int updateByPrimaryKeySelective(CommonDictTreePath record);

    int updateByPrimaryKey(CommonDictTreePath record);
}