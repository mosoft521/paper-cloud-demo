package com.gmail.mosoft521.paper.dao.ext;

import com.gmail.mosoft521.paper.dao.CommonDictTreePathMapper;
import com.gmail.mosoft521.paper.entity.CommonDictTreePath;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonDictTreePathMapperExt extends CommonDictTreePathMapper {
    /**
     * 根据祖先DictID，查找所有子孙Dict路径信息（包括自身）
     *
     * @param ancDictId 祖先DictID
     * @return 所有ancDictId的子孙DictId路径信息（包括自身）
     */
    List<CommonDictTreePath> findTreeByAncDictIdIncludeSelf(Long ancDictId);

    /**
     * 根据子孙DictID，查找所有祖先Dict路径信息（包括自身）
     *
     * @param desDictId 子孙DictID
     * @return 所有desDictId的祖先DictId路径信息（包括自身）
     */
    List<CommonDictTreePath> findTreeByDesDictIdIncludeSelf(Long desDictId);

    /**
     * 根据路径长度，查找所有指定路径长度的字典路径（包括结点部分信息）
     *
     * @param pathLength 路径长度
     * @return 所有指定路径长度的字典路径（包括结点部分信息）
     */
    List<CommonDictTreePathExt> findAllByPathLenth(Integer pathLength);
}
