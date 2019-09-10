package com.scriptterror.bot;

import com.google.common.eventbus.EventBus;
import com.scriptterror.bot.event.StartPollEvent;
import com.scriptterror.bot.model.ChatState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static com.scriptterror.bot.model.ChatState.ON_START;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

/*
   https://github.com/rubenlagus/TelegramBots/tree/master/TelegramBots.wiki/abilities
 */
@Service
@Slf4j
public class Bot extends AbilityBot {

    private int creatorId;
    private final EventBus eventBus;
    private final ResponseHandler responseHandler;

    @Autowired
    public Bot(@Value("${bot.api-key}") String apiKey,
               @Value("${bot.name}") String name,
               @Value("${bot.creatorId}") int creatorId,
               EventBus eventBus,
               DefaultBotOptions botOptions) {
        super(apiKey, name, botOptions);
        this.eventBus = eventBus;
        this.responseHandler = new ResponseHandler(sender, db);//Todo Сделать красивее
        this.creatorId = creatorId;
    }

    @Override
    public int creatorId() {
        return creatorId;
    }


    public Ability replyToStart() {
        return Ability.builder()
                .name("start")
                .info("Start")
                .locality(Locality.USER)
                .input(0)
                .privacy(Privacy.PUBLIC)
                .action(action -> {
                    try {
                        sender.execute(new SendMessage()
                                .setChatId(action.chatId())
                                .setText(Constants.START_MESSAGE)
                                .setReplyMarkup(KeyBoardFactory.withDiscountType()));

                    } catch (TelegramApiException e) {
                        log.error("Some trouble with API", e);
                    }
                })
//                .reply(action -> System.out.println(action.getCallbackQuery().getData()))
                .build();
    }


    public Reply replyToChooseButtons() {
        Consumer<Update> action = msg -> responseHandler.replyToChooseButtons(getChatId(msg), msg.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }


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
            StartPollEvent event = new StartPollEvent();
            event.setChatId(chatId);
            event.setRestaurantName(buttonId);
            event.setOperationEvent(UUID.randomUUID());
            eventBus.post(event);
        }
    }


}
