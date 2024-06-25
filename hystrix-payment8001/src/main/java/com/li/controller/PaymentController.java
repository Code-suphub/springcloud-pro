package com.li.controller;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import com.li.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PaymentController {
    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @RequestMapping("/test/hystrix/ok/{id}")
    public String testHystrixOk(@PathVariable("id") Integer id) {
        return paymentHystrixService.testHystrixOk(id);
    }

    // 三秒以内执行完毕就是正常的业务逻辑,如果超过三秒 就去执行备用方法
    @HystrixCommand(fallbackMethod = "testHystrixTimoutFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    @RequestMapping("/test/hystrix/timeout/{id}")
    public String testHystrixTimeout(@PathVariable("id") Integer id) throws InterruptedException {
        return paymentHystrixService.testHystrixTimeout(id);
    }

    // 兜底方法！
    public String testHystrixTimoutFallback(@PathVariable("id") Integer id) throws InterruptedException {
        return paymentHystrixService.testHystrixTimeout(id) + "降级服务方法执行!";
    }

    @HystrixCommand(fallbackMethod = "testRongDuan_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")}//失败率达到多少后跳闸
    )//在一个10秒钟的窗口期，如果有10个请求 60%都失败了 就熔断
    @RequestMapping("/test/hystrix/rongduan/{id}")
    public String testRongDuan(@PathVariable Integer id) {
        if (id < 0) {
            // 这样做的目的是让他去执行兜底方法
            throw new RuntimeException("id 不能为负数");
        }
        return "服务执行成功! id: " + id;
    }

    public String testRongDuan_fallback(@PathVariable Integer id) {

        return "id 不能为负数,降级服务执行…………  id:" + id;
    }

    @Value("${server.port}")
    private String port;

    @PostMapping("/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        log.info(payment.toString());
        int i = paymentHystrixService.create(payment);
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
        Payment paymentById = paymentHystrixService.getPaymentById(id);
        if (paymentById != null) {
            return new CommonResult<Payment>(200, "查询成功, 端口号" + port, paymentById);
        } else {
            return new CommonResult<Payment>(444, "未查询到, 端口号" + port);
        }

    }

    @RequestMapping("/feign/get/time/out")
    public String timeOut() throws InterruptedException {
        Thread.sleep(3000);

        return port;
    }
}