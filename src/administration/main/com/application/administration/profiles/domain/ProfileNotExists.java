package com.application.administration.profiles.domain;

import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.DomainError;

public final class ProfileNotExists extends DomainError {
    public ProfileNotExists(ProfileId id) {
        super("profile_not_exists", String.format("The profile <%s> doesn't exist", id.value()));
    }

    public ProfileNotExists(UserId id) {
        super("profile_not_exists", String.format("The profile <%s> doesn't exist", id.value()));
    }
}
