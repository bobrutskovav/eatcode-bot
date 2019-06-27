package com.scriptterror.bot;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class Bot extends TelegramLongPollingBot {


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

    @Override
    public String getBotUsername() {
        return "EatCode com.scriptterror.bot.Bot";
    }

    @Override
    public String getBotToken() {
        return "EatCode com.scriptterror.bot.Bot token";
    }





}
