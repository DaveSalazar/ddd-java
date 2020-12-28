package com.application.administration.profiles.application;

import com.application.administration.profiles.domain.Profile;
import com.application.shared.domain.bus.query.Response;

public class ProfileResponse implements Response {

    private final String id;
    private final String userId;
    private final String firstName;
    private final String lastName;

    public ProfileResponse(String id, String userId, String firstName, String lastName) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static ProfileResponse fromAggregate(Profile profile) {
        return new ProfileResponse(
            profile.id().value(),
            profile.userId().value(),
            profile.firstName() != null ? profile.firstName().value() : "",
            profile.lastName() != null ? profile.lastName().value() : ""
        );
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
