package com.scriptterror.bot.config;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
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


    @Bean
    public DefaultBotOptions botOptions(Proxy proxy) {
        DefaultBotOptions options = new DefaultBotOptions();
        options.setProxyHost(proxy.getHost());
        options.setProxyPort(proxy.getPort());
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(proxy.getUser(), proxy.getPassword().toCharArray());
            }
        });

        options.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return options;
    }

}
