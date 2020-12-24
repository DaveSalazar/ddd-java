package com.application.administration.users.application.save;

import com.application.shared.domain.bus.command.Command;

public class CreateUserCommand implements Command {

    private final String id;
    private final String email;
    private final String password;

    public CreateUserCommand(String id, String email, String password) {
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
