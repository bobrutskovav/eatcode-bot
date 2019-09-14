package com.scriptterror.bot.event;

import lombok.Data;

@Data
public class StartPollEvent {

    private String restaurantName;

    private long chatId;



}
