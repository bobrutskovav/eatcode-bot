package com.scriptterror.pollrunner.service;


import com.scriptterror.pollrunner.model.DiscountCodeReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DiscountCodeSender {


    private RestTemplate restTemplate = new RestTemplate();

    private String endpoint = "/telegram-bot/discountcode";
    private String resultEndpoint;

    public DiscountCodeSender(@Value("${zuul.host}") String host,
                              @Value("${zuul.port}") String port) {
        this.resultEndpoint = String.format("http://%s:%s%s", host, port, endpoint);

    }

    public void sendDiscountCodeToBot(String code, long operationId) {
        DiscountCodeReport report = new DiscountCodeReport();
        report.setDiscountCode(code);
        report.setChatId(operationId);
        restTemplate.postForLocation(resultEndpoint, report);
    }
}
