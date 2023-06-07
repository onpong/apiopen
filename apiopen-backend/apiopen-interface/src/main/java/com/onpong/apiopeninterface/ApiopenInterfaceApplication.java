package com.onpong.apiopeninterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//如果不加scanBasePackages就扫描不到ApiOpenClient这个bean
@SpringBootApplication(scanBasePackages="com.onpong.*")
public class ApiopenInterfaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiopenInterfaceApplication.class, args);
    }

}
