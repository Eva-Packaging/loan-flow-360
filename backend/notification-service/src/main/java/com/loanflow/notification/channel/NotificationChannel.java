package com.loanflow.notification.channel;

public interface NotificationChannel {
    String channelName();

    boolean supports(NotificationMessage message);

    DeliveryResult send(NotificationMessage message);

    default String formatSubject(NotificationMessage message) {
        return "[LoanFlow] " + message.getSubject();
    }

}
