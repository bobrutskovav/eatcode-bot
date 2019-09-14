package com.scriptterror.bot;

import com.google.common.eventbus.Subscribe;
import com.scriptterror.bot.event.StartPollEvent;
import com.scriptterror.bot.model.DiscountCodeRequest;
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


        DiscountCodeRequest request = new DiscountCodeRequest();
        request.setChatId(event.getChatId());
        restTemplate.postForEntity(endpoint, request, String.class);

    }
}
