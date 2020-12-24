package com.application.administration.users.application;

import com.application.shared.domain.bus.query.Response;

import java.util.List;

public class UsersResponse implements Response {
    private final List<UserResponse> users;

    public UsersResponse(List<UserResponse> users) {
        this.users = users;
    }

    public List<UserResponse> getUsers() {
        return users;
    }
}
