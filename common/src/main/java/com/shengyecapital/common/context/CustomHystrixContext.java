package com.shengyecapital.common.context;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

public class CustomHystrixContext {

    private static final CustomHystrixContext context = new CustomHystrixContext();

    private static final HystrixRequestVariableDefault<Integer> userIdVariable = new HystrixRequestVariableDefault<>();

    public static CustomHystrixContext getInstance() {
        return context;
    }

    public synchronized void setUserId(Integer _userId) {
        userIdVariable.set(_userId);
    }

    public synchronized Integer getUserId() {
        return userIdVariable.get();
    }

}
