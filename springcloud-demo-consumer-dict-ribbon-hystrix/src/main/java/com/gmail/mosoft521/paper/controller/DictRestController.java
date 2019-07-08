package com.gmail.mosoft521.paper.controller;

import com.gmail.mosoft521.paper.entity.CommonDict;
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
}
