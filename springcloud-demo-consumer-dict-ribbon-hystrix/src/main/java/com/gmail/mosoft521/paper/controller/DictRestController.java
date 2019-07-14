package com.gmail.mosoft521.paper.controller;

import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.vo.TreeVo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
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
    @GetMapping("/dict/findSonsByParentDictIdIncludeSelf/{parentDictId}")
    public List<TreeVo> findSonsByParentDictIdIncludeSelf(@PathVariable Long parentDictId) {
        List<TreeVo> treeVoList = new ArrayList<TreeVo>();
        CommonDict commonDictSelf = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findCommonDictByDictId/" + parentDictId, CommonDict.class);
        TreeVo treeVo = new TreeVo();
        treeVo.setId(commonDictSelf.getDictId().toString());
        treeVo.setParent("#");
        treeVo.setText(commonDictSelf.getDictCodeText());
        treeVoList.add(treeVo);

        CommonDict[] commonDicts = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findSonsByParentDictId/" + parentDictId, CommonDict[].class);
        List<CommonDict> commonDictList = Arrays.asList(commonDicts);

        for (CommonDict commonDict : commonDictList) {
            treeVo = new TreeVo();
            treeVo.setId(commonDict.getDictId().toString());
            treeVo.setParent(commonDictSelf.getDictId().toString());
            treeVo.setText(commonDict.getDictCodeText());
            //TODO:展开子
//            treeVO.setChildren(expand(commonDict.getDictId()));
            treeVoList.add(treeVo);
        }
        return treeVoList;
    }

    private List<TreeVo> expand(Long parentDictId) {
        CommonDict[] commonDicts = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findSonsByParentDictId/" + parentDictId, CommonDict[].class);
        List<CommonDict> commonDictList = Arrays.asList(commonDicts);
        List<TreeVo> treeVoList = new ArrayList<TreeVo>(commonDictList.size());
        for (CommonDict commonDict : commonDictList) {
            TreeVo treeVo = new TreeVo();
            treeVo.setId(commonDict.getDictId().toString());
            treeVo.setParent(parentDictId.toString());
            treeVo.setText(commonDict.getDictCodeText());

            treeVoList.add(treeVo);
            treeVoList.addAll(expand(commonDict.getDictId()));
        }
        return treeVoList;
    }

    public List<TreeVo> findSonsByParentDictIdIncludeSelfFallback(Long parentDictId) {
        List<TreeVo> treeVoList = new ArrayList<TreeVo>();
        TreeVo treeVo = new TreeVo();
        treeVo.setId("1");
        treeVo.setParent("#");
        treeVo.setText("DEFAULT");
        treeVoList.add(treeVo);
        return treeVoList;
    }

    @HystrixCommand(fallbackMethod = "insertDictFallback")
    @PostMapping("/dict")
    public TreeVo insertDict(@RequestBody TreeVo treeVo) {
        CommonDict commonDict = new CommonDict();
        commonDict.setDictCodeText(treeVo.getText());
        CommonDict commonDictResult = this.restTemplate.postForObject("http://springcloud-demo-provider-dict/insertDict/" + treeVo.getParent() + "/" + treeVo.getText() + "/" + treeVo.getText(), commonDict, CommonDict.class);
        treeVo.setId(commonDictResult.getDictId().toString());
        return treeVo;
    }

    public TreeVo insertDictFallback(@RequestBody TreeVo treeVo) {
        treeVo.setId("-1L");
        return treeVo;
    }
}
