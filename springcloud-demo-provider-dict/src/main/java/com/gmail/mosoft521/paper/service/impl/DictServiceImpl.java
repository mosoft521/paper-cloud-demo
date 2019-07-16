package com.gmail.mosoft521.paper.service.impl;

import com.gmail.mosoft521.paper.constants.DisabledEnum;
import com.gmail.mosoft521.paper.dao.ext.CommonDictMapperExt;
import com.gmail.mosoft521.paper.dao.ext.CommonDictTreePathMapperExt;
import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.entity.CommonDictExample;
import com.gmail.mosoft521.paper.entity.CommonDictTreePath;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathExample;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathExt;
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
        CommonDict commonDict = commonDictMapperExt.selectByPrimaryKey(dictId);
        return commonDict;
    }

    @Override
    public List<CommonDictTreePathExt> findAllByPathLenth(Integer pathLength) {
        return commonDictTreePathMapperExt.findAllByPathLenth(pathLength);
    }

    @Override
    public List<CommonDict> findSonsByParentDictId(Long parentDictId) {
        //方法一：Java代码遍历
//        CommonDictTreePathExample example = new CommonDictTreePathExample();
//        CommonDictTreePathExample.Criteria criteria = example.createCriteria();
//        criteria.andAncDictIdEqualTo(parentDictId);
//        criteria.andPathLengthEqualTo(1);
//        List<CommonDictTreePath> commonDictTreePaths = commonDictTreePathMapperExt.selectByExample(example);
//        List<CommonDict> commonDictList = new ArrayList<>();
//        for (CommonDictTreePath commonDictTreePath : commonDictTreePaths) {
//            CommonDict commonDict = commonDictMapperExt.selectByPrimaryKey(commonDictTreePath.getDesDictId());
//            commonDictList.add(commonDict);
//        }
        //方法二：SQL遍历
        List<CommonDict> commonDictList = commonDictMapperExt.findSonsByParentDictId(parentDictId);
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
        commonDict.setDisabled(DisabledEnum.ENABLED.getCode());
        commonDict.setCreater(1L);
        commonDict.setVersion(1L);
        commonDict.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commonDictMapperExt.insert(commonDict);
        Long selfId = commonDict.getDictId();
        //查询出父的所有祖先路径（包括父）
        List<CommonDictTreePath> parentList = commonDictTreePathMapperExt.findTreeByDesDictIdIncludeSelf(parentId);
        for (int i = 0; i < parentList.size(); i++) {
            CommonDictTreePath commonDictTreePathOld = parentList.get(i);
            CommonDictTreePath commonDictTreePathNew = new CommonDictTreePath();
            commonDictTreePathNew.setAncDictId(commonDictTreePathOld.getAncDictId());
            commonDictTreePathNew.setDesDictId(selfId);
            commonDictTreePathNew.setDisabled(DisabledEnum.ENABLED.getCode());
            commonDictTreePathNew.setCreater(1L);
            commonDictTreePathNew.setVersion(1L);
            commonDictTreePathNew.setCreateTime(new Timestamp(System.currentTimeMillis()));
            int newPathLength = commonDictTreePathOld.getPathLength() + 1;
            commonDictTreePathNew.setPathLength(newPathLength);
            //sortNo计算:以此时的ancDictId为祖先，此时的pathLength，查询出最大的sortNo，再加1为新路径的sortNo
            Integer sortNo = commonDictTreePathMapperExt.findMaxSortNoByAncDictIdAndPathLenth(commonDictTreePathOld.getAncDictId(), newPathLength);
            commonDictTreePathNew.setSortNo(sortNo + 1);
            commonDictTreePathMapperExt.insert(commonDictTreePathNew);
        }
        //添加自身到自身的关系
        CommonDictTreePath commonDictTreePath = new CommonDictTreePath();
        commonDictTreePath.setAncDictId(selfId);
        commonDictTreePath.setDesDictId(selfId);
        commonDictTreePath.setDisabled(DisabledEnum.ENABLED.getCode());
        commonDictTreePath.setCreater(1L);
        commonDictTreePath.setVersion(1L);
        commonDictTreePath.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commonDictTreePath.setPathLength(0);
        commonDictTreePathMapperExt.insert(commonDictTreePath);
        return commonDict;
    }

    @Override
    @Transactional
    public void delDict(Long dictId) {
        //先查询出以dictId为祖先的结点(包裹自身)，并记录之，为以后删除做准备
        List<CommonDict> commonDictList = commonDictMapperExt.findTreeByAncDictIdIncludeSelf(dictId);
        List<Long> delDictIds = new ArrayList<>(commonDictList.size());
        for (CommonDict commonDict : commonDictList) {
            delDictIds.add(commonDict.getDictId());
        }

        //先删除包含这些结点的路径：即祖先dictId含有或者子孙dictId含有的
        CommonDictTreePathExample commonDictTreePathExample = new CommonDictTreePathExample();
        CommonDictTreePathExample.Criteria commonDictTreePathExampleCriteria1 = commonDictTreePathExample.createCriteria();
        commonDictTreePathExampleCriteria1.andAncDictIdIn(delDictIds);
        CommonDictTreePathExample.Criteria commonDictTreePathExampleCriteria2 = commonDictTreePathExample.createCriteria();
        commonDictTreePathExampleCriteria2.andDesDictIdIn(delDictIds);
        commonDictTreePathExample.or(commonDictTreePathExampleCriteria2);
        int count = commonDictTreePathMapperExt.deleteByExample(commonDictTreePathExample);
        //log输出

        //最后删除这些结点
        CommonDictExample commonDictExample = new CommonDictExample();
        CommonDictExample.Criteria commonDictExampleCriteria = commonDictExample.createCriteria();
        commonDictExampleCriteria.andDictIdIn(delDictIds);
        int count2 = commonDictMapperExt.deleteByExample(commonDictExample);
        //log输出
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

    @Override
    @Transactional
    public void moveDict(Long dictId, Long newParentId) {
        //----------------------------------------
        //（一）剪断子树与原祖系的所有路径
        //----------------------------------------

        //①查询出原祖系结点：即以dictId的祖先结点(不包括自身)，并记录之，为以后删除做准备
        List<CommonDict> ancOldDictList = commonDictMapperExt.findTreeByDesDictId(dictId);
        List<Long> ancOldDictIds = new ArrayList<>(ancOldDictList.size());
        for (CommonDict commonDict : ancOldDictList) {
            ancOldDictIds.add(commonDict.getDictId());
        }

        //②查询出子树结点：即以dictId为祖先的结点(包括自身)，并记录之，为以后删除做准备
        List<CommonDict> subCommonDictList = commonDictMapperExt.findTreeByAncDictIdIncludeSelf(dictId);
        List<Long> subDictIds = new ArrayList<>(subCommonDictList.size());
        for (CommonDict commonDict : subCommonDictList) {
            subDictIds.add(commonDict.getDictId());
        }

        //③删除包含这些结点的路径：即祖先dictId含有或者子孙dictId含有的
        CommonDictTreePathExample commonDictTreePathExample = new CommonDictTreePathExample();
        CommonDictTreePathExample.Criteria commonDictTreePathExampleCriteria = commonDictTreePathExample.createCriteria();
        commonDictTreePathExampleCriteria.andAncDictIdIn(ancOldDictIds);
        commonDictTreePathExampleCriteria.andDesDictIdIn(subDictIds);
        int count = commonDictTreePathMapperExt.deleteByExample(commonDictTreePathExample);
        //log输出

        //----------------------------------------
        //（二）添加子树与新祖系路径
        //----------------------------------------

        //④查询出新祖系结点：即以newParentId的祖先结点(包括自身)，【必须按照从子孙到祖先排序，因为查的是以子孙结点为条件，则pathLength升序】并记录之，为以后插入做准备
        List<CommonDict> ancNewDictList = commonDictMapperExt.findTreeByDesDictIdIncludeSelf(newParentId);
        List<Long> ancNewDictIds = new ArrayList<>(ancNewDictList.size());
        for (CommonDict commonDict : ancNewDictList) {
            ancNewDictIds.add(commonDict.getDictId());
        }

        //⑤查询出子树所有路径，并按dtp.anc_dict_id, dtp.des_dict_id,dtp.path_length排序
        List<CommonDictTreePath> subCommonDictTreePathList = commonDictTreePathMapperExt.findTreeByAncDictIdIncludeSelf(dictId);

        //⑥插入路径结点，要求前两步按照一定规律来
        int ancPathLength = 0;
        for (Long ancDictId : ancNewDictIds) {
            ++ancPathLength;
            for (CommonDictTreePath subCommonDictTreePath : subCommonDictTreePathList) {
                //插入路径
                CommonDictTreePath commonDictTreePath = new CommonDictTreePath();
                commonDictTreePath.setAncDictId(ancDictId);
                commonDictTreePath.setDesDictId(subCommonDictTreePath.getDesDictId());
                commonDictTreePath.setCreater(1L);
                commonDictTreePath.setVersion(1L);
                commonDictTreePath.setDisabled(DisabledEnum.ENABLED.getCode());
                commonDictTreePath.setCreateTime(new Timestamp(System.currentTimeMillis()));
                commonDictTreePath.setPathLength(subCommonDictTreePath.getPathLength() + ancPathLength);
                commonDictTreePathMapperExt.insert(commonDictTreePath);
            }
        }

//        //⑦插入点睛之笔路径
//        CommonDictTreePath commonDictTreePath = new CommonDictTreePath();
//        commonDictTreePath.setAncDictId(newParentId);
//        commonDictTreePath.setDesDictId(dictId);
//        commonDictTreePath.setCreater(1L);
//        commonDictTreePath.setVersion(1L);
//        commonDictTreePath.setDisabled(DisabledEnum.ENABLED.getCode());
//        commonDictTreePath.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        commonDictTreePath.setPathLength(1);
//        commonDictTreePathMapperExt.insert(commonDictTreePath);
    }
}