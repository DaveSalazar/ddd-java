package com.application.shared.domain.errors;

import com.application.shared.domain.DomainError;

public class InvalidJsonArrayFormat extends DomainError {
    public InvalidJsonArrayFormat(String value) {
        super("invalid_json_array_format", String.format("Format %s is invalid"));
    }
}
