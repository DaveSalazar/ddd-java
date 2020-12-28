package com.application.administration.profiles.application.find;

import com.application.shared.domain.bus.query.Query;

public class FindProfileQuery implements Query {
    private final String id;

    public FindProfileQuery(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
