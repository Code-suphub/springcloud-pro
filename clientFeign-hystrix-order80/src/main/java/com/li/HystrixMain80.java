package com.li;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableHystrix // 包含了 @EnableCircuitBreaker，不加这个注解，降级熔断的command注解失效
public class HystrixMain80 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixMain80.class, args);
    }
}