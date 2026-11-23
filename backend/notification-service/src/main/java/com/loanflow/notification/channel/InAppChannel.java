package com.loanflow.notification.channel;

import java.time.Instant;

public class InAppChannel implements NotificationChannel {

    @Override
    public String channelName() {
        return "IN_APP";
    }

    @Override
    public boolean supports(NotificationMessage message) {
        return message.getType() == NotificationType.IN_APP;
    }

    @Override
    public DeliveryResult send(NotificationMessage message) {
        if (!supports(message)) {
            return new DeliveryResult("IN_APP", false, Instant.now());
        }
        System.out.println(formatSubject(message));
        return new DeliveryResult("IN_APP", true, Instant.now());
    }
}