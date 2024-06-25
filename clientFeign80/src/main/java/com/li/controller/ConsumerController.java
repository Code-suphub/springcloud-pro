package com.li.controller;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import com.li.remoteService.RemotePaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ConsumerController {
    @Resource
    private RemotePaymentService remotePaymentService;

    @GetMapping("/consumer/get/payment/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id) {
        CommonResult<Payment> paymentById = remotePaymentService.getPaymentById(id);
        return paymentById;
    }

    @RequestMapping("/consumer/feign/get/time/out")
    public String timeOut() throws InterruptedException {

        String s = remotePaymentService.timeOut();
        return s;
    }
}
