package com.loanflow.notification.channel;

public class NotificationMessage {
    private final String recipient;
    private final String subject;
    private final String body;
    private final NotificationType type;

    public NotificationMessage(String recipient, String subject, String body, NotificationType type) {

        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("Recipient cannot be null or blank");
        }
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject cannot be null or blank");
        }

        if (body == null || body.isBlank()) {
            throw new IllegalArgumentException("Body cannot be null or blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.type = type;


    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public NotificationType getType() {
        return type;
    }


}
