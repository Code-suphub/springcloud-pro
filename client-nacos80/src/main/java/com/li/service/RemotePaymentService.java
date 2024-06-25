package com.li.service;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import com.li.service.impl.PaymentRemoteHystrixServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "NACOS-PAYMENT-PROVIDER", fallback = PaymentRemoteHystrixServiceImpl.class)
// 这里的fallback 就是定义了客户侧因为服务侧崩了会调用 PaymentRemoteHystrixServiceImpl 的对应方法进行兜底
public interface RemotePaymentService { // 注意这里是接口

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @PostMapping("/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment);

    @RequestMapping("/feign/get/time/out")
    public String timeOut() throws InterruptedException;

    @RequestMapping("/hystrix/test/timeout/{id}")
    public String testHystrixTimeout(@PathVariable("id") Integer id) throws InterruptedException;

}
