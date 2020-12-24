package com.application.administration.users.application.register;

import com.application.shared.domain.bus.command.Command;

public class RegisterUserCommand implements Command {
    private final String id;
    private final String email;
    private final String password;

    public RegisterUserCommand(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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
