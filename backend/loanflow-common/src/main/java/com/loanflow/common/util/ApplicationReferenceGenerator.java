package com.loanflow.common.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneOffset;

public final class ApplicationReferenceGenerator {
    private ApplicationReferenceGenerator() {}

    private static final SecureRandom random = new SecureRandom();

    public static String generate() {
        LocalDate currentDate = LocalDate.now(ZoneOffset.UTC);
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder suffix = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            suffix.append(alphabet[random.nextInt(alphabet.length)]);
        }

        return String.format("LF-%s-%s", currentDate.format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE), suffix);
    }
}
