package com.scriptterror.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramBotApp {

    public static void main(String[] args) {
//        ApiContextInitializer.init();
//
//        TelegramBotsApi botsApi = new TelegramBotsApi();
//
//        try {
//            botsApi.registerBot(new com.scriptterror.bot.Bot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
//        EventBusFactory.getAsyncEventBus().register(new ReceiptSender());
//        StartPollEvent event = new StartPollEvent();
//        System.out.println("Current thread is " + Thread.currentThread().getName());
//        EventBusFactory.getAsyncEventBus().post(event);

        SpringApplication.run(TelegramBotApp.class);

    }
}

