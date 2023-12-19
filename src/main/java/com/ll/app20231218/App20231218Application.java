package com.ll.app20231218;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class App20231218Application {
    public static void main(String[] args) {
        SpringApplication.run(App20231218Application.class, args);
    }
}