package com.scriptterror.pollrunner.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

@Component
public class ResultsHandler {

    private ConcurrentMap<Long, Future<String>> pollResults = new ConcurrentHashMap<>();

    private DiscountCodeSender discountCodeSender;


    public ResultsHandler(@Autowired DiscountCodeSender discountCodeSender) {
        this.discountCodeSender = discountCodeSender;
    }

    public void registerAsyncResult(long operationId, Future<String> result) {
        pollResults.put(operationId, result);
    }
}
