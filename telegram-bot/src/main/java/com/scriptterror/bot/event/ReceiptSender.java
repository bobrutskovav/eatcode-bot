package com.scriptterror.bot.event;

import com.google.common.eventbus.Subscribe;

public class ReceiptSender {


    @Subscribe
    private void sendRecieptToBack(MessageWithReceiptEvent event){
        System.out.println("In thread " + Thread.currentThread().getName() +  " Event recieved " + event.getName());
    }

}
