package ru.lastenko.butterflyspringintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class ButterflySpringIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ButterflySpringIntegrationApplication.class, args);
    }

}
