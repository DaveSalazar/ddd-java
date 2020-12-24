package com.application.administration.users.domain;

import com.application.shared.domain.value_object.StringValueObject;

public final class UserPassword extends StringValueObject {
    public UserPassword(String value) {
        super(value);
    }
    public UserPassword() {
        super("");
    }
}
