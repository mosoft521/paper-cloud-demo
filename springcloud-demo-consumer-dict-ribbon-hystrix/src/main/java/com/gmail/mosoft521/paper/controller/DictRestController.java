package com.gmail.mosoft521.paper.controller;

import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.vo.TreeVO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DictRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DictRestController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @HystrixCommand(fallbackMethod = "findByIdFallback")
    @GetMapping("/dict/{id}")
    public CommonDict findCommonDictByDictId(@PathVariable Long id) {
        CommonDict commonDict = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findCommonDictByDictId/" + id, CommonDict.class);
        return commonDict;
    }

    public CommonDict findByIdFallback(Long id) {
        CommonDict commonDict = new CommonDict();
        commonDict.setDictId(-1L);
        commonDict.setDictCode("DEFAULT");
        commonDict.setDictCodeText("默认文本");
        return commonDict;
    }

    @GetMapping("/log-user-instance")
    public void logUserInstance() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("springcloud-demo-provider-dict");
        // 打印当前选择的是哪个节点
        DictRestController.LOGGER.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
    }

    @HystrixCommand(fallbackMethod = "findSonsByParentDictIdIncludeSelfFallback")
    @GetMapping("/findSonsByParentDictIdIncludeSelf/{parentDictId}")
    public List<TreeVO> findTreeByAncDictIdIncludeSelf(@PathVariable Long parentDictId) {
        CommonDict[] commonDicts = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findSonsByParentDictIdIncludeSelf/" + parentDictId, CommonDict[].class);
        List<CommonDict> commonDictList = Arrays.asList(commonDicts);
        List<TreeVO> commonDictVOList = new ArrayList<TreeVO>(commonDictList.size());
        for (CommonDict commonDict : commonDictList) {
            System.out.println(commonDict.getDictId());
            TreeVO treeVO = new TreeVO();
            treeVO.setId(commonDict.getDictId());
            treeVO.setText(commonDict.getDictCodeText());
            //TODO:展开子
//            treeVO.setChildren(expand(commonDict.getDictId()));
            commonDictVOList.add(treeVO);
        }
        return commonDictVOList;
    }

    private List<TreeVO> expand(Long parentDictId) {
        CommonDict[] commonDicts = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findSonsByParentDictIdIncludeSelf/" + parentDictId, CommonDict[].class);
        List<CommonDict> commonDictList = Arrays.asList(commonDicts);
        List<TreeVO> commonDictVOList = new ArrayList<TreeVO>(commonDictList.size());
        for (CommonDict commonDict : commonDictList) {
            System.out.println(commonDict.getDictId());
            TreeVO treeVO = new TreeVO();
            treeVO.setId(commonDict.getDictId());
            treeVO.setText(commonDict.getDictCodeText());

            treeVO.setChildren(expand(commonDict.getDictId()));
            commonDictVOList.add(treeVO);
        }
        return commonDictVOList;
    }

    public CommonDict findSonsByParentDictIdIncludeSelfFallback(Long parentDictId) {
        CommonDict commonDict = new CommonDict();
        commonDict.setDictId(-1L);
        commonDict.setDictCode("DEFAULT");
        commonDict.setDictCodeText("默认文本");
        return commonDict;
    }
}
