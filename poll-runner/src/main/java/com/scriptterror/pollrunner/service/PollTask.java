package com.scriptterror.pollrunner.service;

import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.Map;
import java.util.concurrent.Future;

public interface PollTask {


    Future<String> makePoll(Map<String, Long> paramsForPoll,
                            SuccessCallback<String> successCallback,
                            FailureCallback failureCallback);
}
