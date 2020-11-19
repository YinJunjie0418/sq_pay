package org.yeepay.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages={"org.yeepay"})
public class YeePayPayAppliaction {
    public static void main(String[] args) {
        SpringApplication.run(YeePayPayAppliaction.class, args);
    }
}
