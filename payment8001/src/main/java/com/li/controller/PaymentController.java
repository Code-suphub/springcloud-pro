package com.li.controller;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import com.li.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private DiscoveryClient discoveryClient;

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

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("***** element:" + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    @RequestMapping("/feign/get/time/out")
    public String timeOut() throws InterruptedException {
        Thread.sleep(3000);

        return port;
    }
}
