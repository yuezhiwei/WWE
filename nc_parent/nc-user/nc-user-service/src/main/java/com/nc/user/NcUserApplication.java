package com.nc.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.nc.user.mapper")
public class NcUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(NcUserApplication.class, args);
    }
}