package com.scriptterror.bot;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Service
public class Bot extends TelegramLongPollingBot {
 //TODO переделать бота под AbilityBot https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-abilities

    @Value("${bot.api-key}")
    private String apiKey;


    private final EventBus eventBus;


    @Autowired
    public Bot(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasPhoto()){
            //ToDo Вытащить фотку и отправить её куда нибудь на парсинг в отдельном потоке, ответить человеку.
            SendMessage message = new SendMessage();
            eventBus.post("Receipt");
        }

    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getBotUsername() {
        return "EatCodeBot";
    }

    @Override
    public String getBotToken() {
        return apiKey;
    }





}
