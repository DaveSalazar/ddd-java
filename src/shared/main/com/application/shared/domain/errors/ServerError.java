package com.application.shared.domain.errors;

import com.application.shared.domain.DomainError;

public class ServerError extends DomainError {
    public ServerError() {
        super("server_error", String.format("An error has occurred."));
    }
}
