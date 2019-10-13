package com.scriptterror.bot;

import com.google.common.eventbus.Subscribe;
import com.scriptterror.bot.event.StartPollEvent;
import com.scriptterror.bot.model.DiscountCodeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class HttpClient {

    @Value("${zuul.host}")
    private String zuulHost;

    @Autowired
    private RestTemplate restTemplate;


    @Subscribe
    private void sendPollTask(StartPollEvent event) {
        String endpoint = String.format("http://%s/poll-runner-%s/discountcode", zuulHost, event.getRestaurantName());
        log.debug("Send a StartPollEvent {} to {}", event, endpoint);


        DiscountCodeRequest request = new DiscountCodeRequest();
        request.setChatId(event.getChatId());
        restTemplate.postForEntity(endpoint, request, String.class);

    }
}
