package com.application.administration.users.application.find;


import com.application.shared.domain.bus.query.Query;

public class FindUserQuery implements Query {

    private final String id;

    public FindUserQuery(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
