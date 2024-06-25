package com.li.service;

import com.li.entities.Payment;

public interface PaymentHystrixService {
    public String testHystrixOk(Integer id);

    public String testHystrixTimeout(Integer id) throws InterruptedException;

    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
