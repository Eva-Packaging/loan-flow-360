package com.loanflow.common.util;

public final class IdempotencyKeyUtil {
    private IdempotencyKeyUtil() {}

    public static String build(String eventId, String consumerGroup) {
        return String.format("%s:%s", eventId, consumerGroup);
    }

    public static boolean isValid(String idempotencyKey) {
        return idempotencyKey != null && idempotencyKey.contains(":");
    }
}
