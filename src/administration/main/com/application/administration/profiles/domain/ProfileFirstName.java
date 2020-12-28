package com.application.administration.profiles.domain;

import com.application.shared.domain.value_object.StringValueObject;

public class ProfileFirstName extends StringValueObject {
    public ProfileFirstName(String value) {
        super(value);
    }

    public ProfileFirstName() { super("");}
}
