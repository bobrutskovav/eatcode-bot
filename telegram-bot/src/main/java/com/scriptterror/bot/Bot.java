package com.scriptterror.bot;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


/*
   https://github.com/rubenlagus/TelegramBots/tree/master/TelegramBots.wiki/abilities
 */
@Service
public class Bot extends AbilityBot {


    private int creatorId;
    private final EventBus eventBus;


    @Autowired
    public Bot(@Value("${bot.api-key}") String apiKey,
               @Value("${bot.name}") String name,
               @Value("${bot.creatorId}") int creatorId,
               EventBus eventBus,
               DefaultBotOptions botOptions) {
        super(apiKey, name, botOptions);
        this.eventBus = eventBus;
        this.creatorId = creatorId;
    }

    @Override
    public int creatorId() {
        return creatorId;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasPhoto()){
            //ToDo Вытащить фотку и отправить её куда нибудь на парсинг в отдельном потоке, ответить человеку.
            SendMessage message = new SendMessage();
            eventBus.post("Receipt");
        }

    }


}
