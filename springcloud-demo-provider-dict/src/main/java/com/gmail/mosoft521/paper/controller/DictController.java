package com.gmail.mosoft521.paper.controller;

import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathExt;
import com.gmail.mosoft521.paper.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DictController {
    @Autowired
    private DictService dictService;

    @GetMapping("/findCommonDictByDictId/{id}")
    public CommonDict findCommonDictByDictId(@PathVariable Long id) {
        CommonDict commonDict = this.dictService.findCommonDictByDictId(id);
        return commonDict;
    }

    @GetMapping("/findAllByPathLenth/{pathLength}")
    public List<CommonDictTreePathExt> findAllByPathLenth(@PathVariable Integer pathLength) {
        List<CommonDictTreePathExt> commonDictTreePathExtList = this.dictService.findAllByPathLenth(pathLength);
        return commonDictTreePathExtList;
    }

    @GetMapping("/findSonsByParentDictId/{parentDictId}")
    public List<CommonDict> findSonsByParentDictId(@PathVariable Long parentDictId) {
        List<CommonDict> commonDictList = this.dictService.findSonsByParentDictId(parentDictId);
        return commonDictList;
    }

    @GetMapping("/findSonsByParentDictIdIncludeSelf/{parentDictId}")
    public List<CommonDict> findSonsByParentDictIdIncludeSelf(@PathVariable Long parentDictId) {
        List<CommonDict> commonDictList = this.dictService.findSonsByParentDictIdIncludeSelf(parentDictId);
        return commonDictList;
    }

    @GetMapping("/findTreeByAncDictIdIncludeSelf/{parentDictId}")
    public List<CommonDict> findTreeByAncDictIdIncludeSelf(@PathVariable Long parentDictId) {
        List<CommonDict> commonDictList = this.dictService.findTreeByAncDictIdIncludeSelf(parentDictId);
        return commonDictList;
    }

    @PostMapping("/insertDict/{parentId}/{code}/{codeText}")
    public CommonDict insertDict(@PathVariable Long parentId, @PathVariable String code, @PathVariable String codeText) {
        CommonDict commonDict = this.dictService.insertDict(parentId, code, codeText);
        return commonDict;
    }

    @PutMapping("/modifyDict")
    public CommonDict modifyCommonDict(@RequestBody CommonDict commonDict) {
        CommonDict commonDictResult = this.dictService.modifyCommonDict(commonDict);
        return commonDictResult;
    }

    @DeleteMapping("/delDict/{dictId}")
    public void delDict(@PathVariable Long dictId) {
        this.dictService.delDict(dictId);
    }

    @DeleteMapping("/moveDict/{id}/{newParentId}")
    public void moveDict(@PathVariable Long id, @PathVariable Long newParentId) {
        this.dictService.moveDict(id, newParentId);
    }
}
