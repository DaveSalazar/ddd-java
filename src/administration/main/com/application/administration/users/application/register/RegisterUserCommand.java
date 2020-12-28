package com.application.administration.users.application.register;

import com.application.shared.domain.bus.command.Command;

public class RegisterUserCommand implements Command {
    private final String id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;

    public RegisterUserCommand(String id, String email, String firstName, String lastName, String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String password() {
        return password;
    }
}
