package com.li;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // 标注当前服务是Eurekad客户端，并注册进服务端中
public class PaymentApplication8001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication8001.class, args);
    }
}
