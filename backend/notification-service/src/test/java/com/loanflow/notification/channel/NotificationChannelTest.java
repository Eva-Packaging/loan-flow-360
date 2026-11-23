package com.loanflow.notification.channel;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationChannelTest {

    @Test
    void TestChannel() {

        List<NotificationChannel> channels = List.of(
                new EmailChannel(),
                new SmsChannel(),
                new InAppChannel()
        );

        for (NotificationChannel channel : channels) {

            NotificationType type = switch (channel.channelName()) {
                case "EMAIL" -> NotificationType.EMAIL;
                case "SMS" -> NotificationType.SMS;
                case "IN_APP" -> NotificationType.IN_APP;
                default -> throw new IllegalStateException(
                        "Unknown channel: " + channel.channelName()
                );
            };

            NotificationMessage message = new NotificationMessage(
                    "alice@example.com",
                    "Payment Reminder",
                    "Your payment is due tomorrow.",
                    type
            );

            assertTrue(channel.supports(message));

            DeliveryResult result = channel.send(message);

            assertTrue(result.isSuccessFlag());
        }
    }
}