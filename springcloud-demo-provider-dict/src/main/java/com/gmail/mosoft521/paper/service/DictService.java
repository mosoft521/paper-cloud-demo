package com.gmail.mosoft521.paper.service;

import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.entity.CommonDictTreePath;

import java.util.List;
import java.util.Map;

/**
 * 字典service接口
 */
public interface DictService {
    /**
     * 根据dictId查询字典
     *
     * @param dictId 字典ID
     * @return 字典详情
     */
    CommonDict findCommonDictByDictId(Long dictId);


    //查询子：

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
     * 插入字典
     *
     * @param parentId 字典父Id，默认有个根节点0
     * @param code     字典代码
     * @param codeText 字典文本
     * @return true：成功；false：失败。
     */
    CommonDict insertDict(Long parentId, String code, String codeText);

    /**
     * 按主键删除字典
     *
     * @param dictId 字典ID
     * @return true：成功；false：失败。
     */
    void delDict(Long dictId);

    /**
     * 根据条件查询字典内容
     *
     * @param condtionMap
     */
    Map<String, Object> findByParentDictId(Map<String, Long> condtionMap);

    /**
     * 修改字典类型数据（如：商户类型）
     *
     * @param commonDict
     * @return
     */
    CommonDict modifyCommonDict(CommonDict commonDict);
}