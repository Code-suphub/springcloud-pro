package com.li.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient("CLOUD-PROVIDER-HYSTRIX-PAYMENT")
public interface PaymentRemoteHystrixService {

    @RequestMapping("/test/hystrix/ok/{id}")
    public String testHystrixOk(@PathVariable("id") Integer id);

    @RequestMapping("/test/hystrix/timeout/{id}")
    public String testHystrixTimeout(@PathVariable("id") Integer id) throws InterruptedException;

}
