package com.scriptterror.bot;

import com.google.common.eventbus.Subscribe;
import com.scriptterror.bot.event.StartPollEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class HttpClient {

    @Value("${zuul.host}")
    private String zuulHost;
    @Value("${zuul.port}")
    private int zuulPort;

    private RestTemplate restTemplate = new RestTemplate();


    @Subscribe
    private void sendPollTask(StartPollEvent event) {
        String endpoint = String.format("http://%s:%d/poll-runner-%s/discountcode", zuulHost, zuulPort, event.getRestaurantName());
        log.debug("Send a StartPollEvent {} to {}", event, endpoint);

        restTemplate.postForEntity(endpoint, event, String.class);
        //  restTemplate.exchange(endpoint, HttpMethod.POST);//ToDo отправить пост в зуул, принять его на таскконтроллере поллраннера,
        System.out.println("In thread " + Thread.currentThread().getName() + " Event recieved " + event.getName());
    }
}
