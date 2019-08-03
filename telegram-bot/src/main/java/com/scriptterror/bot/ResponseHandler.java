package com.scriptterror.bot;

import com.scriptterror.bot.model.ChatState;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

import static com.scriptterror.bot.model.ChatState.ON_START;


public class ResponseHandler {

    private final MessageSender sender;
    private final Map<Long, ChatState> chatStates;

    public ResponseHandler(MessageSender sender, DBContext dbContext) {
        this.sender = sender;
        this.chatStates = dbContext.getMap("CHAT_STATES");
    }


    public void replyToStart(long chatId) {
        try {
            sender.execute(new SendMessage()
                    .setText(Constants.START_MESSAGE)
                    .setChatId(chatId));
            chatStates.put(chatId, ON_START);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToChooseButtons(Long chatId, String buttonId) {
        //ToDo отправить сообщение в шину с айди чата и айди кнопки, поставить статус чату что он ожидает кода, по заврешению отправить код в чат.
        System.out.println(String.format("Right now get a buttonId %s from chatId %s", buttonId, chatId));
    }
}
