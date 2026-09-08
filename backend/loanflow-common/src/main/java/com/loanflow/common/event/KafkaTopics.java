package com.loanflow.common.event;

public final class KafkaTopics {
    private KafkaTopics() {}

    public static final String LOAN_APPLICATION_SUBMITTED = "loan.application.submitted";
    public static final String LOAN_DOCUMENT_VERIFIED = "loan.document.verified";
    public static final String LOAN_UNDERWRITING_REQUESTED = "loan.underwriting.requested";
    public static final String LOAN_UNDERWRITING_COMPLETED = "loan.underwriting.completed";
    public static final String LOAN_STATUS_UPDATED = "loan.status.updated";
    public static final String LOAN_NOTIFICATIONS_SEND = "loan.notifications.send";
}
