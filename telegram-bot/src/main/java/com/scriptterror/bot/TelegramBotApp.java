package com.scriptterror.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class TelegramBotApp {

    public static void main(String[] args) {

        SpringApplication.run(TelegramBotApp.class);

    }
}

