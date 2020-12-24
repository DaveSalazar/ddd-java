package com.application.administration.users.application.login;

import com.application.shared.domain.bus.query.Query;

public class AuthenticateUserQuery implements Query {
    private final String email;
    private final String password;

    public AuthenticateUserQuery(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email() {return email; }
    public String password() {return password; }
}
