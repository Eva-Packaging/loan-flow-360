package com.loanflow.notification.channel;

import java.time.Instant;

public class SmsChannel implements NotificationChannel {

    @Override
    public String channelName() {
        return "SMS";
    }

    @Override
    public boolean supports(NotificationMessage message) {
        return message.getType() == NotificationType.SMS
                && message.getBody().length() <= 160;
    }

    @Override
    public DeliveryResult send(NotificationMessage message) {
        if (!supports(message)) {
            return new DeliveryResult("SMS", false, Instant.now());
        }
        System.out.println(message.getBody());

        return new DeliveryResult("SMS", true, Instant.now());
    }
}