package com.li.controller;


import com.li.aop.anotation.Check;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Check
    @GetMapping("/need")
    public String testNeed() {
        return "need";
    }

    @GetMapping("/noNeed")
    public String get() {
        return "noNeed";
    }
}
