package com.li.remoteService;

import com.li.entities.CommonResult;
import com.li.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient("PAYMENT") // 这里声明的客户端必须和provider提供的一致 @FeignClient("provider微服务名字")
public interface RemotePaymentService {

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);//这里声明的方法签名，必须和provider服务中的controller中方法的签名一致

    @RequestMapping("/feign/get/time/out")
    public String timeOut() throws InterruptedException;
}
