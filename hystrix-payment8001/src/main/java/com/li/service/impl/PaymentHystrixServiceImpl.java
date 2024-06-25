package com.li.service.impl;

import com.li.dao.PaymentDao;
import com.li.entities.Payment;
import com.li.service.PaymentHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentHystrixServiceImpl implements PaymentHystrixService {
    @Override
    public String testHystrixOk(Integer id) {
        return "测试成功 + testHystrixOk() -->" + id;
    }

    @Override
    public String testHystrixTimeout(Integer id) throws InterruptedException {
        Thread.sleep(3000);

        return "测试成功 + testHystrixTimeout() -->" + id;
    }

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }

}
