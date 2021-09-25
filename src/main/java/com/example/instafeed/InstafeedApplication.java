package com.example.instafeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class InstafeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstafeedApplication.class, args);
    }

}
