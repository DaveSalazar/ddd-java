package com.application.administration.profiles.application.save;

import com.application.shared.domain.bus.command.Command;

public class CreateProfileCommand implements Command {
    private final String id;
    private final String userId;
    private final String firstName;
    private final String lastName;

    public CreateProfileCommand(
            String id,
            String userId,
            String firstName,
            String lastName
    ) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String id() {
        return id;
    }

    public String userId() {
        return userId;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }


}
