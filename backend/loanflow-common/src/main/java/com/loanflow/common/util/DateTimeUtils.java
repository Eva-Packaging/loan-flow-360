package com.loanflow.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class DateTimeUtils {
    private DateTimeUtils() {}

    public static String toIsoUtcString(Instant instant) {
        return instant.toString();
    }

    public static LocalDateTime toLocalDateTime(Instant instant, ZoneId zoneId) {
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    public static Instant nowUtc() {
        return Instant.now();
    }
}
