package com.application.administration.profiles.application.find_by_user_id;

import com.application.shared.domain.bus.query.Query;

public class FindProfileByUserQuery implements Query {
    private final String id;

    public FindProfileByUserQuery(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
