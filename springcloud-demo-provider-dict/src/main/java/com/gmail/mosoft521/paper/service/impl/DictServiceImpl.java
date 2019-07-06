package com.gmail.mosoft521.paper.service.impl;


import com.gmail.mosoft521.paper.constants.DisabledType;
import com.gmail.mosoft521.paper.dao.ext.CommonDictMapperExt;
import com.gmail.mosoft521.paper.dao.ext.CommonDictTreePathMapperExt;
import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.entity.CommonDictTreePath;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathExample;
import com.gmail.mosoft521.paper.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private CommonDictMapperExt commonDictMapperExt;

    @Autowired
    private CommonDictTreePathMapperExt commonDictTreePathMapperExt;

    @Override
    public CommonDict findCommonDictByDictId(Long dictId) {
        return commonDictMapperExt.selectByPrimaryKey(dictId);
    }

    @Override
    public List<CommonDict> findSonsByParentDictId(Long parentDictId) {
        //方法一：Java代码遍历
        CommonDictTreePathExample example = new CommonDictTreePathExample();
        CommonDictTreePathExample.Criteria criteria = example.createCriteria();
        criteria.andAncDictIdEqualTo(parentDictId);
        List<CommonDictTreePath> commonDictTreePaths = commonDictTreePathMapperExt.selectByExample(example);
        List<CommonDict> commonDictList = new ArrayList<>();
        for (CommonDictTreePath commonDictTreePath : commonDictTreePaths) {
            CommonDict commonDict = commonDictMapperExt.selectByPrimaryKey(commonDictTreePath.getDesDictId());
            commonDictList.add(commonDict);
        }
        //TODO:方法二：SQL遍历
        commonDictList = commonDictMapperExt.findSonsByParentDictId(parentDictId);
        return commonDictList;
    }

    @Override
    public List<CommonDict> findSonsByParentDictIdIncludeSelf(Long parentDictId) {
        return commonDictMapperExt.findSonsByParentDictIdIncludeSelf(parentDictId);
    }

    @Override
    public List<CommonDict> findTreeByAncDictId(Long ancDictId) {
        return commonDictMapperExt.findTreeByAncDictId(ancDictId);
    }

    @Override
    public List<CommonDict> findTreeByAncDictIdIncludeSelf(Long ancDictId) {
        return commonDictMapperExt.findTreeByAncDictIdIncludeSelf(ancDictId);
    }

    @Override
    public List<CommonDict> findTreeByDesDictId(Long desDictId) {
        return commonDictMapperExt.findTreeByDesDictId(desDictId);
    }

    @Override
    public List<CommonDict> findTreeByDesDictIdIncludeSelf(Long desDictId) {
        return commonDictMapperExt.findTreeByDesDictIdIncludeSelf(desDictId);
    }


    /**
     * 插入字典
     *
     * @param parentId 字典父Id，默认有个根节点0
     * @param code     字典代码
     * @param codeText 字典文本
     * @return true：成功；false：失败。
     */
    @Override
    @Transactional
    public CommonDict insertDict(Long parentId, String code, String codeText) {
        CommonDict commonDict = new CommonDict();
        commonDict.setDictCode(code);
        commonDict.setDictCodeText(codeText);
        commonDict.setDisabled(DisabledType.ENABLED.getCode());
        commonDict.setVersion(1L);
        commonDict.setCreater(null);
        commonDict.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commonDictMapperExt.insert(commonDict);
        Long selfId = commonDict.getDictId();
        //查询出父的所有祖先路径（包括父）
        List<CommonDictTreePath> parentList = commonDictTreePathMapperExt.findTreeByDesDictIdIncludeSelf(parentId);
        List<CommonDictTreePath> commonDictTreePathList = new ArrayList<CommonDictTreePath>(parentList.size());
        for (int i = 0; i < parentList.size(); i++) {
            CommonDictTreePath commonDictTreePathOld = parentList.get(i);
            CommonDictTreePath commonDictTreePathNew = new CommonDictTreePath();
            commonDictTreePathNew.setAncDictId(commonDictTreePathOld.getAncDictId());
            commonDictTreePathNew.setDesDictId(selfId);
            commonDictTreePathNew.setDisabled(DisabledType.ENABLED.getCode());
            commonDictTreePathNew.setVersion(1L);
            commonDictTreePathNew.setCreater(null);
            commonDictTreePathNew.setCreateTime(new Timestamp(System.currentTimeMillis()));
            commonDictTreePathNew.setPathLength(commonDictTreePathOld.getPathLength() + 1);
            commonDictTreePathList.add(commonDictTreePathNew);
        }
        //添加自身到自身的关系
        CommonDictTreePath commonDictTreePath = new CommonDictTreePath();
        commonDictTreePath.setAncDictId(selfId);
        commonDictTreePath.setDesDictId(selfId);
        commonDictTreePath.setVersion(1L);
        commonDictTreePath.setDisabled(DisabledType.ENABLED.getCode());
        commonDictTreePath.setPathLength(0);
        commonDictTreePathList.add(commonDictTreePath);
        for (CommonDictTreePath commonDictTreePath1 : commonDictTreePathList) {
            commonDictTreePathMapperExt.insert(commonDictTreePath1);
        }
        return commonDict;
    }

    @Override
    public void delDict(Long dictId) {
        //todo:删除字典！
        commonDictMapperExt.deleteByPrimaryKey(dictId);
    }

    @Override
    public Map<String, Object> findByParentDictId(Map<String, Long> condtionMap) {
        List<Map<String, Object>> listMap = commonDictMapperExt.findByParentDictId(condtionMap);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (Map<String, Object> map : listMap) {
            resultMap.put(map.get("dictId").toString(), map.get("dictCodeText"));
        }
        return resultMap;
    }

    @Override
    @Transactional
    public CommonDict modifyCommonDict(CommonDict commonDict) {
        commonDictMapperExt.updateByPrimaryKeySelective(commonDict);
        return commonDict;
    }
}