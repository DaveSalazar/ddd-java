package com.application.shared.domain;

import com.application.shared.domain.value_object.StringValueObject;

public final class VideoUrl extends StringValueObject {
    public VideoUrl(String value) {
        super(value);
    }

    public VideoUrl() {
        super(null);
    }
}
