package de.fimatas.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.fimatas")
public class FimatasDeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FimatasDeApplication.class, args);
    }
}
