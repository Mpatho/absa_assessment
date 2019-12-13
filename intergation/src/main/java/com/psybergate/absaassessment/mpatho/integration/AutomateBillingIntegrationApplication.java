package com.psybergate.absaassessment.mpatho.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AutomateBillingIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomateBillingIntegrationApplication.class, args);
    }

}
