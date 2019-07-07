package com.gmail.mosoft521.paper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.gmail.mosoft521.paper.dao")
//@ComponentScan("${application.basepackage}")
public class ProviderDictApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderDictApplication.class, args);
    }
}
