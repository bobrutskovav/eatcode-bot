package com.scriptterror.bot.controller;


import com.scriptterror.bot.Bot;
import com.scriptterror.bot.model.DiscountCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class PollRunnerTaskResultController {


    private Bot bot;

    public PollRunnerTaskResultController(@Autowired Bot bot) {
        this.bot = bot;
    }

    @PostMapping("/discountcode")
    public void discountСode(DiscountCodeResponse response) {
        log.info("Received a DiscountCode {} for operationId {}", response.getDiscountCode(), response.getOperationId());


    }
}
