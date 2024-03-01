package com.malykhnik.technicaltask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TechnicalTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnicalTaskApplication.class, args);
    }

}
