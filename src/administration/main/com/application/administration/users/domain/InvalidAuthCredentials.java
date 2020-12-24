package com.application.administration.users.domain;

import com.application.shared.domain.DomainError;

public class InvalidAuthCredentials extends DomainError {

    public InvalidAuthCredentials() {
        super("invalid_auth_credentials", "Credentials are not valid");
    }
}
