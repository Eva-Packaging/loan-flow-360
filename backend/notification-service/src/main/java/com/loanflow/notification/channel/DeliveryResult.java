package com.loanflow.notification.channel;

import java.time.Instant;

public class DeliveryResult {
    private final String channelName;
    private final boolean successFlag;
    private final Instant timestamp;

    public String getChannelName(){
        return channelName;
    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public DeliveryResult(String channelName, boolean successFlag, Instant timestamp){
        this.channelName=channelName;
        this.successFlag=successFlag;
        this.timestamp=timestamp;
    }




}
