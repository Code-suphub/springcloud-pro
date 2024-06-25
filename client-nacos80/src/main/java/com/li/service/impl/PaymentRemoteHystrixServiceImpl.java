package com.li.service.impl;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import com.li.service.RemotePaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentRemoteHystrixServiceImpl implements RemotePaymentService {
    @Override
    public CommonResult<Payment> getPaymentById(Long id) {
        return new CommonResult(500, "对方服务端以宕机!无法使用!");
    }

    @Override
    public CommonResult<Payment> create(Payment payment) {
        return new CommonResult(500, "对方服务端以宕机!无法使用!");
    }

    @Override
    public String timeOut() throws InterruptedException {
        return "对方服务端以宕机!无法使用!";
    }

    @Override
    public String testHystrixTimeout(Integer id) throws InterruptedException {
        return "对方服务端以宕机!无法使用!";
    }
}
