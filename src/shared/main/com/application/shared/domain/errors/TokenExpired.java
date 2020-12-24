package com.application.shared.domain.errors;

import com.application.shared.domain.DomainError;

public class TokenExpired extends DomainError {

    public TokenExpired() {
        super("token_expired", String.format("Session Expired"));
    }
}
