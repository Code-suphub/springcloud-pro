package com.li.controller;


import com.li.entities.CommonResult;
import com.li.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    private static String url = "http://payment/payment/";

    @GetMapping("/consumer/payment/create/")
    public CommonResult<Payment> create(Payment payment) {
        //使用 postForEntity 内部发的是post请求
        ResponseEntity<CommonResult> commonResultResponseEntity =
                restTemplate.postForEntity(url + "create/", payment, CommonResult.class);

        return commonResultResponseEntity.getBody();

    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable Long id) {
        // getForObject() 内部发get 请求
        return restTemplate.getForObject(url + "get/" + id, CommonResult.class);
    }
}
