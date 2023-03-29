package com.superdog.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@EnableSwagger2
@EnableCaching
@EnableScheduling
@MapperScan({"com.superdog.springboot","com.lczyfz"})
@ComponentScan(basePackages = {"com.superdog.springboot.*","com.lczyfz.*"})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        SecurityAutoConfiguration.class})
public class ExamSystemMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExamSystemMainApplication.class,args);
    }
}
