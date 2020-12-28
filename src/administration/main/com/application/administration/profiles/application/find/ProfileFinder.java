package com.application.administration.profiles.application.find;

import com.application.administration.profiles.application.ProfileResponse;
import com.application.administration.profiles.domain.ProfileNotExists;
import com.application.administration.profiles.domain.ProfileRepository;
import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.shared.domain.Service;

@Service
public class ProfileFinder {
    private final ProfileRepository repository;

    public ProfileFinder(ProfileRepository repository) {
        this.repository = repository;
    }

    public ProfileResponse find(ProfileId profileId) {
        return repository.search(profileId)
                .map(ProfileResponse::fromAggregate)
                .orElseThrow(() -> new ProfileNotExists(profileId));
    }
}
