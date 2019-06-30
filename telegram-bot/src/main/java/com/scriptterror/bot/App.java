package com.scriptterror.bot;


import com.scriptterror.bot.event.MessageWithReceiptEvent;
import com.scriptterror.bot.event.ReceiptSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class App {

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
//        MessageWithReceiptEvent event = new MessageWithReceiptEvent();
//        System.out.println("Current thread is " + Thread.currentThread().getName());
//        EventBusFactory.getAsyncEventBus().post(event);

         SpringApplication.run(App.class);

    }
}

