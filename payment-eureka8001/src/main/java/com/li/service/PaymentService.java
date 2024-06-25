package com.li.service;

import com.li.entities.Payment;

public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(Long id);

    // 模拟请求超时
    String testHystrixTimeout(Integer id) throws InterruptedException;
}
