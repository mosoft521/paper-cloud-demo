package com.gmail.mosoft521.paper;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerNativeApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigServerNativeApplication.class).web(true).run(args);
    }

}
