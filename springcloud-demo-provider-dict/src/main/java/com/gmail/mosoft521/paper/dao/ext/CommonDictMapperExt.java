package com.gmail.mosoft521.paper.dao.ext;

import com.gmail.mosoft521.paper.dao.CommonDictMapper;
import com.gmail.mosoft521.paper.entity.CommonDict;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 对应的SQL都有点绕，BE CAUTION
 */
@Repository
public interface CommonDictMapperExt extends CommonDictMapper {
    /**
     * 根据父DictID，查找直接子孙DictID，即儿子节点（不包括自身）
     *
     * @param parentDictId 父DictID
     * @return 所有以ancDictId为父亲的儿子DictId列表（不包括自身）
     */
    List<CommonDict> findSonsByParentDictId(Long parentDictId);

    /**
     * 根据父DictID，查找直接子孙DictID，即儿子节点（包括自身）
     *
     * @param parentDictId 父DictID
     * @return 所有以ancDictId为父亲的儿子DictId列表（包括自身）
     */
    List<CommonDict> findSonsByParentDictIdIncludeSelf(Long parentDictId);

    //查询树：

    /**
     * 根据祖先DictID，查找所有子孙DictID（不包括自身）
     *
     * @param ancDictId 祖先DictID
     * @return 所有以ancDictId为祖先的子孙DictId列表（不包括自身）
     */
    List<CommonDict> findTreeByAncDictId(Long ancDictId);

    /**
     * 根据祖先DictID，查找所有子孙DictID（包括自身）
     *
     * @param ancDictId 祖先DictID
     * @return 所有以ancDictId为祖先的子孙DictId列表（包括自身）
     */
    List<CommonDict> findTreeByAncDictIdIncludeSelf(Long ancDictId);

    /**
     * 根据子孙DictID，查找所有祖先DictID（不包括自身）
     *
     * @param desDictId 子孙DictID
     * @return 所有desDictId的祖先DictId列表（不包括自身）
     */
    List<CommonDict> findTreeByDesDictId(Long desDictId);

    /**
     * 根据子孙DictID，查找所有祖先DictID（包括自身）
     *
     * @param desDictId 子孙DictID
     * @return 所有desDictId的祖先DictId列表（包括自身）
     */
    List<CommonDict> findTreeByDesDictIdIncludeSelf(Long desDictId);

    /**
     * 根据条件查询字典内容
     *
     * @param condtionMap
     * @return
     */
    List<Map<String, Object>> findByParentDictId(Map<String, Long> condtionMap);
}
