package com.application.shared.domain.errors;

import com.application.shared.domain.DomainError;

public class AppVersionMismatch extends DomainError {
    public AppVersionMismatch() {
        super("app_version_mismatch", String.format("App version deprecated"));
    }
}
