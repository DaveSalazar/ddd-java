package com.application.administration.users.application;


import com.application.administration.users.domain.User;
import com.application.shared.domain.bus.query.Response;

public class UserResponse implements Response {
    private final String id;
    private final String email;
    private final String password;

    public UserResponse(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static UserResponse fromAggregate(User user) {
        return new UserResponse(
                user.id().value(),
                user.email().value(),
                user.password().value());

    }

    public String id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }
}
