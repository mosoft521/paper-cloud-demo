package com.gmail.mosoft521.paper.controller;

import com.gmail.mosoft521.paper.dto.BaseDTO;
import com.gmail.mosoft521.paper.entity.CommonDict;
import com.gmail.mosoft521.paper.entity.CommonDictTreePathExt;
import com.gmail.mosoft521.paper.vo.TreeVo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        List<TreeVo> treeVoList = new ArrayList<TreeVo>();
//        CommonDict commonDictSelf = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findCommonDictByDictId/" + parentDictId, CommonDict.class);
//        TreeVo treeVo = new TreeVo();
//        treeVo.setId(commonDictSelf.getDictId().toString());
//        treeVo.setParent("#");
//        treeVo.setText(commonDictSelf.getDictCodeText());
//        treeVoList.add(treeVo);
//
//        CommonDict[] commonDicts = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findSonsByParentDictId/" + parentDictId, CommonDict[].class);
//        List<CommonDict> commonDictList = Arrays.asList(commonDicts);
//
//        for (CommonDict commonDict : commonDictList) {
//            treeVo = new TreeVo();
//            treeVo.setId(commonDict.getDictId().toString());
//            treeVo.setParent(commonDictSelf.getDictId().toString());
//            treeVo.setText(commonDict.getDictCodeText());
//            //展开子
////            treeVO.setChildren(expand(commonDict.getDictId()));
//            treeVoList.add(treeVo);
//        }
//        return treeVoList;

        //满足jsTree第二种格式的树：一下子全部加载treePath length为1的
        List<TreeVo> treeVoList = new ArrayList<TreeVo>();
        CommonDict commonDictSelf = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findCommonDictByDictId/" + parentDictId, CommonDict.class);
        TreeVo treeVo = new TreeVo();
        treeVo.setId(commonDictSelf.getDictId().toString());
        treeVo.setParent("#");
        treeVo.setText(commonDictSelf.getDictCodeText());
        treeVo.setState("open");
        Map<String,String> liAttr = new HashMap<String,String>();
        liAttr.put("sortNo","0");
        treeVo.setLi_attr(liAttr);
        treeVoList.add(treeVo);

        CommonDictTreePathExt[] commonDictTreePathExts = this.restTemplate.getForObject("http://springcloud-demo-provider-dict/findAllByPathLenth/1", CommonDictTreePathExt[].class);
        List<CommonDictTreePathExt> commonDictTreePathExtList = Arrays.asList(commonDictTreePathExts);

        for (CommonDictTreePathExt commonDictTreePathExt : commonDictTreePathExtList) {
            treeVo = new TreeVo();
            treeVo.setId(commonDictTreePathExt.getDesDictId().toString());
            treeVo.setParent(commonDictTreePathExt.getAncDictId().toString());
            treeVo.setText(commonDictTreePathExt.getDesDictCodeText());
            treeVo.setState("open");
            liAttr = new HashMap<String,String>();
            liAttr.put("sortNo",commonDictTreePathExt.getSortNo().toString());
            treeVo.setLi_attr(liAttr);
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
            treeVo.setState("open");

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
        treeVo.setState("open");
        treeVoList.add(treeVo);
        return treeVoList;
    }

    @HystrixCommand(fallbackMethod = "insertDictFallback")
    @PostMapping("/insertDict")
    public TreeVo insertDict(@RequestBody TreeVo treeVo) {
        CommonDict commonDict = new CommonDict();
        commonDict.setDictCode(treeVo.getText());
        commonDict.setDictCodeText(treeVo.getText());
        CommonDict commonDictResult = this.restTemplate.postForObject("http://springcloud-demo-provider-dict/insertDict/" + treeVo.getParent() + "/" + treeVo.getText() + "/" + treeVo.getText(), commonDict, CommonDict.class);
        treeVo.setId(commonDictResult.getDictId().toString());
        return treeVo;
    }

    public TreeVo insertDictFallback(@RequestBody TreeVo treeVo) {
        treeVo.setId("-1L");
        return treeVo;
    }

    @HystrixCommand(fallbackMethod = "modifyDictFallback")
    @PutMapping("/modifyDict")
    public BaseDTO modifyDict(@RequestBody TreeVo treeVo) {
        BaseDTO baseDTO = new BaseDTO();
        CommonDict commonDict = new CommonDict();
        commonDict.setDictId(Long.parseLong(treeVo.getId()));
        commonDict.setDictCode(treeVo.getText());
        commonDict.setDictCodeText(treeVo.getText());
        commonDict.setModifier(1L);
        commonDict.setModifyTime(new Timestamp(System.currentTimeMillis()));
        this.restTemplate.put("http://springcloud-demo-provider-dict/modifyDict", commonDict);
        baseDTO.setCode(200);
        baseDTO.setMessage("success");
        return baseDTO;
    }

    public BaseDTO modifyDictFallback(@RequestBody TreeVo treeVo) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setCode(400);
        baseDTO.setMessage("fail");
        return baseDTO;
    }

    @HystrixCommand(fallbackMethod = "deleteDictFallback")
    @DeleteMapping("/delDict/{id}")
    public BaseDTO deleteDict(@PathVariable Long id) {
        BaseDTO baseDTO = new BaseDTO();
        this.restTemplate.delete("http://springcloud-demo-provider-dict/delDict/" + id);
        baseDTO.setCode(200);
        baseDTO.setMessage("success");
        return baseDTO;
    }

    public BaseDTO deleteDictFallback(@PathVariable Long id) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setCode(400);
        baseDTO.setMessage("fail");
        return baseDTO;
    }

    @HystrixCommand(fallbackMethod = "moveDictFallback")
    @DeleteMapping("/moveDict/{id}/{newParentId}")
    public BaseDTO moveDict(@PathVariable Long id, @PathVariable Long newParentId) {
        BaseDTO baseDTO = new BaseDTO();
        this.restTemplate.delete("http://springcloud-demo-provider-dict/moveDict/" + id + "/" + newParentId);
        baseDTO.setCode(200);
        baseDTO.setMessage("success");
        return baseDTO;
    }

    public BaseDTO moveDictFallback(@PathVariable Long id, @PathVariable Long newParentId) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setCode(400);
        baseDTO.setMessage("fail");
        return baseDTO;
    }
}
