package com.application.administration.users.domain;

import com.application.shared.domain.DomainError;

public class UserEmailNotExist extends DomainError {
    public UserEmailNotExist(UserEmail email) {
        super("collaborator_not_exist", String.format("The user <%s> doesn't exist", email.value()));
    }
}
