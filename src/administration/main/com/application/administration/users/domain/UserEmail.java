package com.application.administration.users.domain;

import com.application.shared.domain.value_object.StringValueObject;

public final class UserEmail extends StringValueObject {
    public UserEmail(String value) {
        super(value);
    }
    public UserEmail() {
        super("");
    }
}
