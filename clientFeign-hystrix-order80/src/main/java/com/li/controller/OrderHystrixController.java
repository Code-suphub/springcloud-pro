package com.li.controller;

import com.li.service.PaymentRemoteHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@DefaultProperties(defaultFallback = "globalHandler")
public class OrderHystrixController {
    @Autowired
    private PaymentRemoteHystrixService paymentRemoteHystrixService;

    @RequestMapping("/consumer/test/hystrix/ok/{id}")
    public String testHystrixOk(@PathVariable("id") Integer id) {
        return paymentRemoteHystrixService.testHystrixOk(id);
    }

    // 精准打击  定义了fallbackMethod 属性
    @HystrixCommand(fallbackMethod = "testHystrix")
    @RequestMapping("/consumer/test/hystrix/timeout/{id}")
    public String testHystrixTimeout(@PathVariable("id") Integer id) throws InterruptedException {
        int i = 10 / 0;
        return paymentRemoteHystrixService.testHystrixTimeout(id);
    }

    // 使用全局服务降级配置
    @HystrixCommand
    @RequestMapping("/consumer/test/hystrix/default/{id}")
    public String test1(@PathVariable("id") Integer id) throws InterruptedException {
        int i = 10 / 0;
        return paymentRemoteHystrixService.testHystrixTimeout(id);
    }

    public String testHystrix(@PathVariable("id") Integer id) {
        return "客户端80 服务降级启动!";
    }

    public String globalHandler() {
        return "全局兜底方法!!";
    }

}
