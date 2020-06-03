package com.nc.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.nc.item.mapper")
public class NcItemServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(NcItemServiceApplication.class,args);
    }
}
