package com.application.administration.profiles.domain;

import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.AggregateRoot;

public class  Profile  extends AggregateRoot {

    private final ProfileId id;
    private final UserId userId;
    private final ProfileFirstName firstName;
    private final ProfileLastName lastName;

    public Profile(ProfileId id, UserId userId, ProfileFirstName firstName, ProfileLastName lastName) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Profile() {
        this.id = null;
        this.userId = null;
        this.firstName = null;
        this.lastName = null;
    }

    public ProfileId id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public ProfileFirstName firstName() {
        return firstName;
    }

    public ProfileLastName lastName() {
        return lastName;
    }
}
