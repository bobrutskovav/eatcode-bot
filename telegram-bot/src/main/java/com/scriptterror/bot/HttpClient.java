package com.scriptterror.bot;

import com.google.common.eventbus.Subscribe;
import com.scriptterror.bot.event.StartPollEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {

    @Value("${zuul.host}")
    private String zuulHost;
    @Value("${zuul.port}")
    private int zuulPort;

    private RestTemplate restTemplate = new RestTemplate();


    @Subscribe
    private void sendPollTask(StartPollEvent event) {
        String endpoint = String.format("http://%s:%d/%s/discountcode", zuulHost, zuulPort, event.getRestaurantName());
        restTemplate.exchange()//ToDo отправить пост в зуул, принять его на таскконтроллере поллраннера
        System.out.println("In thread " + Thread.currentThread().getName() + " Event recieved " + event.getName());
    }
}
