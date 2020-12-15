package com.github.novikovmn.spring2.controller;

import com.github.novikovmn.spring2.service.EurekaClientService;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaClientController {
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Autowired
    private EurekaClientService service;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/zaymi-deneg")
    public String forALoan() {
        return service.giveForLoan();
    }

    @GetMapping("/na-deneg")
    public String takeMoney() {
        return service.takeMoney();
    }
}