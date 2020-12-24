package com.application.administration.users.domain;

import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.DomainError;

public class UserNotExist extends DomainError {
    public UserNotExist(UserId id) {
        super("user_not_exist", String.format("The user <%s> doesn't exist", id.value()));
    }

    public UserNotExist(UserEmail email) {
        super("user_not_exist", String.format("The user <%s> doesn't exist", email.value()));
    }
}
