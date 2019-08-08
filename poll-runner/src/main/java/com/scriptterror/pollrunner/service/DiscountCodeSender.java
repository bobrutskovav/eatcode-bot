package com.scriptterror.pollrunner.service;


import com.scriptterror.pollrunner.model.DiscountCodeReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DiscountCodeSender {


    private RestTemplate restTemplate = new RestTemplate();

    private String host;
    private String endpoint;
    private String port;
    private String resultEndpoint;

    public DiscountCodeSender(@Value("${bot.host}") String host,
                              @Value("${bot.port}") String port,
                              @Value("${bot.endpoint}") String endpoint) {
        this.host = host;
        this.port = port;
        this.endpoint = endpoint;
        this.resultEndpoint = String.format("http://%s:%s%s", host, port, endpoint);

    }

    public void sendDiscountCodeToBot(String code, long operationId) {
        DiscountCodeReport report = new DiscountCodeReport();
        report.setDiscountCode(code);
        report.setOperationId(operationId);
        restTemplate.postForLocation(resultEndpoint, report);
    }
}
