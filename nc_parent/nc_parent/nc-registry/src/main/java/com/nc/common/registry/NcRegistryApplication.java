package com.nc.common.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NcRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(NcRegistryApplication.class,args);
    }


}
