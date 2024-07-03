package com.li.controller;

import com.li.entities.Get;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {
    @GetMapping("/get")
    public String getIndex(@RequestBody Get get, @RequestParam String id) {
        System.out.println(get);
        System.out.println(id);
        return get.toString() + id;
    }

    @PostMapping("/post")
    public String postIndex(@RequestBody Get get, @RequestParam String id) {
        System.out.println(get);
        System.out.println(id);
        return get.toString() + id;
    }
}
