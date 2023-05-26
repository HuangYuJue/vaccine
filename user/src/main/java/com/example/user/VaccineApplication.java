package com.example.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example")//开启feign调用服务
public class VaccineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccineApplication.class, args);
    }

}
