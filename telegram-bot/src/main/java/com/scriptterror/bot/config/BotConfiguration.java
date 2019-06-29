package com.scriptterror.bot.config;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.util.concurrent.Executors;

@SpringBootConfiguration
@EnableEncryptableProperties
public class BotConfiguration {
    static {
        ApiContextInitializer.init();
    }

    @Bean
    public EventBus eventBus(){
        return new AsyncEventBus(Executors.newCachedThreadPool());
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }

}
