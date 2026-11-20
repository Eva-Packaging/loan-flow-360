package com.loanflow.loanservice;

import com.loanflow.common.exception.ValidationException;

public class LoanTermParser {

    public int parse (String input) {
        try {
            int months = Integer.parseInt(input);
            if (months < 6 || months > 360) {
                throw new ValidationException(
                        "Loan term must be between 6 and 360 months"
                );
            }
            return months;
        } catch (NumberFormatException ex) {
            ValidationException validationException = new ValidationException(
                    "Invalid loan term: " + input
            );

            validationException.initCause(ex);
            throw validationException;
        }
    }
}
