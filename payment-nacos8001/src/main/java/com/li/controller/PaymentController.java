package com.li.controller;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import com.li.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j // 自动注入log bean
@RestController
@DefaultProperties(defaultFallback = "globalHandler")
// 标注在类上，表示没有指定`@HystrisCommand(fallbackMethod="方法名")`的方法就是用`@DefaultProperties(defaultFallback="方法名")`所指定的做备用方法 ，全局默认的超时时间是 hystrix默认的超时时间，也就是1秒
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String port;

    @PostMapping("/payment/create/")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        log.info(payment.toString());
        int i = paymentService.create(payment);
        if (i > 0) {
            return new CommonResult(200, "创建成功, 端口号" + port, i);
        } else {
            return new CommonResult(404, "创建失败，端口号" + port, i);
        }

    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        if (paymentById != null) {
            return new CommonResult(200, "查询成功, 端口号" + port, paymentById);
        } else {
            return new CommonResult(444, "未查询到, 端口号" + port);
        }
    }

    @GetMapping("/payment/test")
    public String getPaymentById() {
        return "test";
    }

    @RequestMapping("/feign/get/time/out") // 模拟请求超时，验证Feign的超时控制
    public String timeOut() throws InterruptedException {
        Thread.sleep(3000);
        return port;
    }

    // 三秒以内执行完毕就是正常的业务逻辑,如果超过三秒 就去执行备用方法 testHystrixTimoutFallback ，精准打击  定义了 fallbackMethod 属性，不会使用全局降级配置
    @HystrixCommand(fallbackMethod = "testHystrixTimoutFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    @RequestMapping("/hystrix/test/timeout/{id}")
    public String testHystrixTimeout(@PathVariable("id") Integer id) throws InterruptedException {
        return paymentService.testHystrixTimeout(id);
    }

    // 开启熔断机制，在一个10秒钟的窗口期，如果有10个请求 60%都失败了 就熔断，然后你再执行一定成功的请求都不会执行成功了！一段时间后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发，如果成功，断路器关闭，如果失败，断路器继续进入打开状态，休眠时间窗重新计时
    @HystrixCommand(fallbackMethod = "testRongDuan_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")}//失败率达到多少后跳闸
    )
    @RequestMapping("/test/hystrix/rongduan/{id}")
    public String testRongDuan(@PathVariable Integer id) {
        if (id < 0) {
            // 这样做的目的是让他去执行兜底方法
            throw new RuntimeException("id 不能为负数");
        }
        return "服务执行成功! id: " + id;
    }

    // 使用全局服务降级配置，如果不加，不会启用降级服务
    @HystrixCommand
    @RequestMapping("/consumer/test/hystrix/default/{id}")
    public String testHystrixTimeoutDefault(@PathVariable("id") Integer id) throws InterruptedException {
        int i = 10 / 0;
        return paymentService.testHystrixTimeout(id);
    }

    // 兜底方法！
    public String testHystrixTimoutFallback(@PathVariable("id") Integer id) throws InterruptedException {
        return paymentService.testHystrixTimeout(id) + "降级服务方法执行!";
    }

    // 全局兜底方法！默认全局兜底方法不能有参数
    public String globalHandler() {
        return "全局兜底方法!!";
    }
}
