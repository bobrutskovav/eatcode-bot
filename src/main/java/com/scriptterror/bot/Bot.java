package com.scriptterror.bot;

import com.google.common.eventbus.EventBus;
import com.scriptterror.bot.event.EventBusFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    private EventBus eventBus = EventBusFactory.getAsyncEventBus();

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
