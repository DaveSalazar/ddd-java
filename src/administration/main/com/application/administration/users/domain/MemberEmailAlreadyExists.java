package com.application.administration.users.domain;

import com.application.shared.domain.DomainError;

public class MemberEmailAlreadyExists extends DomainError {

    public MemberEmailAlreadyExists(UserEmail email) {
        super("member_email_already_exist", String.format("The email <%s> already exist", email.value()));
    }
}
