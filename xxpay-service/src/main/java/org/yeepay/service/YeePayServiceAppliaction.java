package org.yeepay.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages={"org.yeepay"})
public class YeePayServiceAppliaction {
    public static void main(String[] args) {
        SpringApplication.run(YeePayServiceAppliaction.class, args);
    }
}
