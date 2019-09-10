package com.scriptterror.pollrunner.controller;


import com.scriptterror.pollrunner.model.DiscountCodeRequest;
import com.scriptterror.pollrunner.service.DiscountCodeSender;
import com.scriptterror.pollrunner.service.PollTask;
import com.scriptterror.pollrunner.service.ResultsHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Slf4j
@RestController
public class TaskController {

    private PollTask task;
    private ResultsHandler handler;
    private DiscountCodeSender discountCodeSender;

    public TaskController(@Autowired PollTask task,
                          @Autowired ResultsHandler resultsHandler,
                          @Autowired DiscountCodeSender codeSender) {
        this.task = task;
        this.handler = resultsHandler;
        this.discountCodeSender = codeSender;
    }


    @PostMapping({"/discountcode"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getDiscount(DiscountCodeRequest request) {
        /*
         *  1. Принять айди.
         *  2. Запустить метод который асинхронно заполняет опрос и возвращает Future.
         *  3. Повесить коллбеки.
         *  3. Сохранить Future.
         *  4. Отправить боту 202.
         *
         *  В том момент Future будет выполнено - отправить боту код
         */
        long operationId = request.getChatId();
        Map<String, Long> params = new HashMap<>();
        params.put("operationId", operationId);
        Future<String> future = task.makePoll(params, discountCode -> {
                    log.debug("Complete making a poll with result : {}", discountCode);
                    discountCodeSender.sendDiscountCodeToBot(discountCode, operationId);
                    //Todo возможно надо сделать через event
                },

                (exception) -> log.error("Error {} on send a discount code to bot! OperationId {} ", exception.getMessage(), operationId)
        );
        handler.registerAsyncResult(operationId, future);


    }
}
