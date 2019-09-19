package com.scriptterror.bot;

import com.google.common.eventbus.EventBus;
import com.scriptterror.bot.event.StartPollEvent;
import com.scriptterror.bot.model.ChatState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;
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
    private final Map<Long, ChatState> chatStates;

    @Autowired
    public Bot(@Value("${bot.api-key}") String apiKey,
               @Value("${bot.name}") String name,
               @Value("${bot.creatorId}") int creatorId,
               EventBus eventBus,
               DefaultBotOptions botOptions) {
        super(apiKey, name, botOptions);
        this.eventBus = eventBus;
        this.creatorId = creatorId;
        chatStates = db.getMap("CHAT_STATES");

    }

    @PostConstruct
    public void postConstruct() {
        chatStates.clear();//что-бы была пустая база
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
                        long chatId = action.chatId();
                        log.debug("Command Start executed by {} {}",
                                action.user().getUserName(),
                                action.update().getMessage());
                        sender.execute(new SendMessage()
                                .setChatId(chatId)
                                .setText(Constants.START_MESSAGE)
                                .setReplyMarkup(KeyBoardFactory.withDiscountType()));

                        if (!chatStates.containsKey(chatId)) {
                            chatStates.put(chatId, ON_START);
                        }
                    } catch (TelegramApiException e) {
                        log.error("Some trouble with API", e);
                    }
                })
                .build();
    }

    public Reply replyToChooseButtons() {
        Consumer<Update> action = msg ->
                replyToChooseButtons(getChatId(msg),
                        msg.getCallbackQuery().getData(),
                        msg.getCallbackQuery().getMessage().getMessageId());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }


    private void replyToChooseButtons(Long chatId, String buttonId, int messageId) {
        if (chatStates.containsKey(chatId)) {
            if (chatStates.get(chatId) == ChatState.AWAITING_FOR_CODE) {
                SendMessage rejectMessage = new SendMessage(chatId, Constants.PLEASE_WAIT_AGAIN);
                try {
                    sender.execute(rejectMessage);
                } catch (TelegramApiException e) {
                    log.error("Problem with telegram api", e);
                }
                return;
            }

        }
        log.trace("Send StartPollEvent to EventBus for chatId {} and ButtonId {}", chatId, buttonId);
        StartPollEvent event = new StartPollEvent();
        event.setChatId(chatId);
        event.setRestaurantName(buttonId);
        eventBus.post(event);
        chatStates.put(chatId, ChatState.AWAITING_FOR_CODE);

        EditMessageReplyMarkup removeKeyBoard = new EditMessageReplyMarkup();
        removeKeyBoard.setChatId(chatId);
        removeKeyBoard.setMessageId(messageId);
        removeKeyBoard.setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<>()));
        SendMessage message = new SendMessage(chatId, Constants.PLEASE_WAIT);
        try {
            sender.execute(removeKeyBoard);
            sender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Problem with telegram api", e);
        }
        //ToDo сделать две очереди, одну на отправку, вторую на получение
    }


    public void sendDiscountCodeToChat(long chatId, String discountCode) {
        log.debug("Sending discount code {} to chat {}", discountCode, chatId);
        try {
            this.execute(new SendMessage(chatId, String.format(Constants.CODE_IS_DONE_FORMAT, discountCode)));
        } catch (TelegramApiException e) {
            log.error("Can't send discount code {} to chatId : {}", discountCode, chatId);
            e.printStackTrace();
        } finally {
            chatStates.remove(chatId);
        }
    }


}
