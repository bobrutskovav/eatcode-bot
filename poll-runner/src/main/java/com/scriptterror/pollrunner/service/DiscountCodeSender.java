package com.scriptterror.pollrunner.service;


import com.scriptterror.pollrunner.model.DiscountCodeReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DiscountCodeSender {


    private RestTemplate restTemplate;

    private String endpoint = "telegram-bot/discountcode";
    private String resultEndpoint;

    public DiscountCodeSender(@Value("${zuul.host}") String host,
                              @Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.resultEndpoint = String.format("http://%s/%s", host, endpoint);

    }

    public void sendDiscountCodeToBot(String code, long operationId) {
        DiscountCodeReport report = new DiscountCodeReport();
        report.setDiscountCode(code);
        report.setChatId(operationId);
        restTemplate.postForLocation(resultEndpoint, report);
    }
}
