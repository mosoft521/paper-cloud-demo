package com.gmail.mosoft521.paper.controller;

import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/findSonsByParentDictIdIncludeSelf/{parentDictId}")
    public List<CommonDict> findSonsByParentDictIdIncludeSelf(@PathVariable Long parentDictId) {
        List<CommonDict> commonDictList = this.dictService.findSonsByParentDictIdIncludeSelf(parentDictId);
        return commonDictList;
    }
}
