package com.scriptterror.bot.event;

import lombok.Data;

import java.util.UUID;

@Data
public class StartPollEvent {

    private String name = "StartPollEvent";

    private String restaurantName;

    private long chatId;

    private UUID operationEvent;

    public String getName() {
        return name;
    }

}
