package com.gmail.mosoft521.paper;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CORSConfig {
    //CORS 跨域访问 Cross-Origin Resource Sharing（跨域资源共享）
    @Configuration
    public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "DELETE", "GET", "POST")
                    .allowedHeaders("*").exposedHeaders("access-control-allow-headers",
                    "access-control-allow-methods", "access-control-allow-origin",
                    "access-control-max-age", "X-Frame-Options")
                    .allowCredentials(false).maxAge(3600);
        }
    }
}
