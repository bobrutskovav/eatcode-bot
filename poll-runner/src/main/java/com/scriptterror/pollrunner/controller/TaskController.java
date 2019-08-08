package com.scriptterror.pollrunner.controller;


import com.scriptterror.pollrunner.model.DiscountCodeRequest;
import com.scriptterror.pollrunner.service.DiscountCodeSender;
import com.scriptterror.pollrunner.service.PollTask;
import com.scriptterror.pollrunner.service.ResultsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;


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


    @GetMapping({"/*/discountcode"})
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
        Future<String> future = task.makePoll(params, result1 -> {
            System.out.println("Result : " + result1);
            String code = result1;
            discountCodeSender.sendDiscountCodeToBot(code, operationId);
            //Todo возможно надо сделать через event
        }, ex -> System.err.println(ex));
        handler.registerAsyncResult(operationId, future);


    }

    //ToDo сделать ендпойнт, который принимает данные от бота.
}
