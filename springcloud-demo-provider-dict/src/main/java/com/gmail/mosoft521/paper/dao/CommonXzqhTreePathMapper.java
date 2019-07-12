package com.gmail.mosoft521.paper.dao;

import com.gmail.mosoft521.paper.entity.CommonXzqhTreePath;
import com.gmail.mosoft521.paper.entity.CommonXzqhTreePathExample;
import com.gmail.mosoft521.paper.entity.CommonXzqhTreePathKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommonXzqhTreePathMapper {
    long countByExample(CommonXzqhTreePathExample example);

    int deleteByExample(CommonXzqhTreePathExample example);

    int deleteByPrimaryKey(CommonXzqhTreePathKey key);

    int insert(CommonXzqhTreePath record);

    int insertSelective(CommonXzqhTreePath record);

    List<CommonXzqhTreePath> selectByExample(CommonXzqhTreePathExample example);

    CommonXzqhTreePath selectByPrimaryKey(CommonXzqhTreePathKey key);

    int updateByExampleSelective(@Param("record") CommonXzqhTreePath record, @Param("example") CommonXzqhTreePathExample example);

    int updateByExample(@Param("record") CommonXzqhTreePath record, @Param("example") CommonXzqhTreePathExample example);

    int updateByPrimaryKeySelective(CommonXzqhTreePath record);

    int updateByPrimaryKey(CommonXzqhTreePath record);
}