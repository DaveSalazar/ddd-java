package com.application.shared.domain.errors;

import com.application.shared.domain.DomainError;

public class InvalidDateFormat extends DomainError {

    public InvalidDateFormat(String date) {
        super("invalid_date_format", String.format("%s format not valid", date));
    }
}
