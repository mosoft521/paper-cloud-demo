package com.gmail.mosoft521.paper.dao.ext;

import com.gmail.mosoft521.paper.dao.CommonDictTreePathMapper;
import com.gmail.mosoft521.paper.entity.CommonDictTreePath;

import java.util.List;

public interface CommonDictTreePathMapperExt extends CommonDictTreePathMapper {
    /**
     * 根据子孙DictID，查找所有祖先Dict路径信息（包括自身）
     *
     * @param desDictId 子孙DictID
     * @return 所有desDictId的祖先DictId路径信息（包括自身）
     */
    List<CommonDictTreePath> findTreeByDesDictIdIncludeSelf(Long desDictId);
}
