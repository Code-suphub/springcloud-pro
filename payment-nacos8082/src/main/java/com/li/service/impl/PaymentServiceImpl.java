package com.li.service.impl;

import com.li.dao.PaymentDao;
import com.li.entities.Payment;
import com.li.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }

    // 模拟请求超时
    @Override
    public String testHystrixTimeout(Integer id) throws InterruptedException {
        Thread.sleep(3000);
        return "测试成功 + testHystrixTimeout() -->" + id;
    }
}
