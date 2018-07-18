package com.grydtech.loadbalancer.controllers;

import com.grydtech.loadbalancer.LoadBalancer;
import com.grydtech.loadbalancer.servicediscovery.Member;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonEndpoint {

    @GetMapping("/test")
    public HttpEntity<String> test(){

        LoadBalancer loadBalancer = LoadBalancer.getInstance();
        Member member = loadBalancer.getLoadBalancedService("order");
        return new HttpEntity<>(member.getName());
    }
}
