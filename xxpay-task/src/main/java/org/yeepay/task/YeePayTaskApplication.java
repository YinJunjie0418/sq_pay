package org.yeepay.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages={"org.yeepay"})
public class YeePayTaskApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(YeePayTaskApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.listeners();
        return application.sources(applicationClass);
    }

    private static Class<YeePayTaskApplication> applicationClass = YeePayTaskApplication.class;

}