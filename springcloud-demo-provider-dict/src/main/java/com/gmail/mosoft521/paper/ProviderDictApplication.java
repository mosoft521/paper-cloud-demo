package com.gmail.mosoft521.paper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProviderDictApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderDictApplication.class, args);
    }
}
