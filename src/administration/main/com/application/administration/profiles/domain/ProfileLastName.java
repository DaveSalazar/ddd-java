package com.application.administration.profiles.domain;

import com.application.shared.domain.value_object.StringValueObject;

public class ProfileLastName extends StringValueObject {
    public ProfileLastName(String value) {
        super(value);
    }

    public ProfileLastName() {
        super("");
    }
}

