package com.scriptterror.pollrunner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PollRunnerApp {

    public static void main(String[] args) {
        SpringApplication.run(PollRunnerApp.class);
    }
}
