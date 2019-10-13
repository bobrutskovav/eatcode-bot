package com.scriptterror.pollrunner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableDiscoveryClient
@SpringBootApplication
@EnableAsync
public class PollRunnerApp {

    public static void main(String[] args) {
        SpringApplication.run(PollRunnerApp.class);
    }
}
