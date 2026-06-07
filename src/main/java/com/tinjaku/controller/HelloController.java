package com.tinjaku.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/hello")
    public String hello(){
        return "Halo tinjaku!";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Admin = jokowiii";
    }
}
