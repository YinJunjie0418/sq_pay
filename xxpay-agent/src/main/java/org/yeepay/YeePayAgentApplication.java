package org.yeepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@ServletComponentScan
public class YeePayAgentApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(YeePayAgentApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.listeners();
        return application.sources(applicationClass);
    }

    private static Class<YeePayAgentApplication> applicationClass = YeePayAgentApplication.class;

}