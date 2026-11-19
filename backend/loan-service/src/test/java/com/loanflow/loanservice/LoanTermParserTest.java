package com.loanflow.loanservice;

import com.loanflow.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTermParserTest {

    @Test
    void shouldPreserveOrginialCause() {

        LoanTermParser parser =
                new LoanTermParser();

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> parser.parse("abc")

                );

        assertNotNull(exception.getCause());
        assertTrue(
                exception.getCause() instanceof NumberFormatException
        );
    }
}