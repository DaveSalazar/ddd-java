package com.application.administration.profiles.application.save;

import com.application.shared.domain.bus.command.Command;

public class CreateProfileCommand implements Command {
    private final String id;
    private final String memberId;
    private final String firstName;
    private final String lastName;
    private final String avatar;
    private final String email;
    private final String username;
    private final String configuration;

    public CreateProfileCommand(
            String id,
            String memberId,
            String firstName,
            String lastName,
            String avatar,
            String email,
            String configuration) {
        this.id = id;
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.configuration = configuration;
        this.email = email;
        this.username = "";
    }


    public String id() {
        return id;
    }

    public String memberId() {
        return memberId;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }


}
