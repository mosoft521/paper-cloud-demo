package com.gmail.mosoft521.paper.service;

import com.gmail.mosoft521.paper.dao.ext.CommonDictMapperExt;
import com.gmail.mosoft521.paper.dao.ext.CommonDictTreePathMapperExt;
import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.entity.CommonDictTreePath;
import com.gmail.mosoft521.paper.service.impl.DictServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class DictServiceTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private CommonDictMapperExt commonDictMapperExt;

    @Mock
    private CommonDictTreePathMapperExt commonDictTreePathMapperExt;

    @InjectMocks
    private DictServiceImpl dictService;

    @Test
    public void insertDictTest() {
        //stub
        Long parentId = 1L;
        String code = "test";
        String codeText = "测试";

        List<CommonDictTreePath> parentList = new ArrayList<CommonDictTreePath>();

        //given
        given(commonDictMapperExt.insert(any(CommonDict.class))).willReturn(1);
        given(commonDictTreePathMapperExt.findTreeByDesDictIdIncludeSelf(parentId)).willReturn(parentList);
        given(commonDictTreePathMapperExt.findMaxSortNoByAncDictIdAndPathLenth(any(Long.class), any(Integer.class))).willReturn(1);
        given(commonDictTreePathMapperExt.insert(any(CommonDictTreePath.class))).willReturn(1);

        //when
        dictService.insertDict(parentId, code, codeText);

        //then
        verify(commonDictMapperExt).insert(any(CommonDict.class));
        verify(commonDictTreePathMapperExt).insert(any(CommonDictTreePath.class));
    }

}
