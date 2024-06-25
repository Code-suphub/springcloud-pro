package com.li.controller;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import com.li.service.RemotePaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private RemotePaymentService remotePaymentService;

    @GetMapping("/consumer/payment/create/")
    public CommonResult<Payment> create(Payment payment) {
        CommonResult<Payment> paymentById = remotePaymentService.create(payment);
        return paymentById;

    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable Long id) {
        CommonResult<Payment> paymentById = remotePaymentService.getPaymentById(id);
        return paymentById;
    }

    @RequestMapping("/consumer/get/feign/time/out")
    public String timeOut() throws InterruptedException {
        String s = remotePaymentService.timeOut();
        return s;
    }

    @RequestMapping("/consumer/hystrix/test/timeout/{id}")
//    // 三秒以内执行完毕就是正常的业务逻辑,如果超过三秒 就去执行备用方法 testHystrixTimoutFallback ，精准打击  定义了 fallbackMethod 属性，不会使用全局降级配置
//    @HystrixCommand(fallbackMethod = "testHystrixTimoutFallback", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public String testHystrixTimeout(@PathVariable("id") Integer id) throws InterruptedException {
        return remotePaymentService.testHystrixTimeout(id);
    }

    // 兜底方法！
    public String testHystrixTimoutFallback(@PathVariable("id") Integer id) throws InterruptedException {
        return remotePaymentService.testHystrixTimeout(id) + "降级服务方法 **客户端** 执行!";
    }
}
