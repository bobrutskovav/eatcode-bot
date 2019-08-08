package com.scriptterror.pollrunner.model;

import lombok.Data;

@Data
public class DiscountCodeRequest {

    private long chatId;

    public long getChatId() {
        return chatId;
    }


    //ToDo здесь будут данные из чека

}
