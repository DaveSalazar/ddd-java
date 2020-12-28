package com.application.administration.profiles.application.save;

import com.application.administration.profiles.domain.*;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.event.EventBus;

@Service
public class ProfileCreator {
    private final ProfileRepository repository;
    private final EventBus eventBus;

    public ProfileCreator(ProfileRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    public void save(ProfileId id, UserId memberId, ProfileFirstName firstName, ProfileLastName lastName) {
        Profile profile = new Profile(id, memberId, firstName, lastName);
        repository.save(profile);
        eventBus.publish(profile.pullDomainEvents());
    }
}
