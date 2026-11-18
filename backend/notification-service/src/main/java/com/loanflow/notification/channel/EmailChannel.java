package com.loanflow.notification.channel;

import java.time.Instant;

public class EmailChannel implements NotificationChannel {

    @Override
    public String channelName() {
        return "EMAIL";
    }

    @Override
    public boolean supports(NotificationMessage message) {
        if (message.getType() == NotificationType.EMAIL) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DeliveryResult send(NotificationMessage message) {
        if (!supports(message)) {
            return new DeliveryResult("EMAIL", false, Instant.now());
        }
        System.out.println(formatSubject(message));
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Body: " + message.getBody());

        return new DeliveryResult("EMAIL", true, Instant.now());
    }

    @Override
    public String formatSubject(NotificationMessage message) {
        return "[LoanFlow EMAIL] " + message.getSubject();
    }
}