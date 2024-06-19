package com.li;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 标注当前服务是一个Eureka的注册中心
public class EurekaApplication7002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication7002.class, args);
    }
}