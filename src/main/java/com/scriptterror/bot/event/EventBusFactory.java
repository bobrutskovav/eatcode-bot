package com.scriptterror.bot.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

public class EventBusFactory {

    private static EventBus asyncEventBus = new AsyncEventBus(Executors.newCachedThreadPool());

    public static EventBus getAsyncEventBus() {
        return asyncEventBus;
    }
}
