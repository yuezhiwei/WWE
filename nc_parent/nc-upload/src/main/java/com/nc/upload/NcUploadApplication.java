package com.nc.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NcUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(NcUploadApplication.class, args);
    }
}