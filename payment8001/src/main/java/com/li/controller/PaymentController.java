package com.li.controller;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import com.li.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        log.info(payment.toString());
        int i = paymentService.create(payment);
        if (i > 0) {
            log.debug("添加成功，id：" + payment.getId());
            return new CommonResult(200, "创建成功, 端口号" + port, i);
        } else {
            log.debug("添加失败");
            return new CommonResult(444, "创建失败，端口号" + port, i);
        }

    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        if (paymentById != null) {
            return new CommonResult<Payment>(200, "查询成功, 端口号" + port, paymentById);
        } else {
            return new CommonResult<Payment>(444, "未查询到, 端口号" + port);
        }

    }
}
