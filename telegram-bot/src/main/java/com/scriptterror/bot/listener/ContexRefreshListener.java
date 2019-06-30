package com.scriptterror.bot.listener;

import com.scriptterror.bot.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


@Component
public class ContexRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = LoggerFactory.getLogger(ContexRefreshListener.class);

    @Autowired
    private TelegramBotsApi api;

    @Autowired
    private Bot telegramBot;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Listener got event {}, try to register telegram bot", event);

        try {
            //TODO Сделать поддержку прокси, которая задается через проперти. https://github.com/rubenlagus/TelegramBots/blob/master/TelegramBots.wiki/Using-Http-Proxy.md
            api.registerBot(telegramBot);

        } catch (TelegramApiRequestException e) {
            log.error("Problems with Bot registration! {}", e);
            throw new RuntimeException(e);
        }

    }
}
