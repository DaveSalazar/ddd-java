package com.application.administration.profiles.application.find_by_user_id;

import com.application.administration.profiles.application.ProfileResponse;
import com.application.administration.profiles.domain.ProfileNotExists;
import com.application.administration.profiles.domain.ProfileRepository;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;

@Service
public class ProfileByUserFinder {
    private final ProfileRepository repository;

    public ProfileByUserFinder(ProfileRepository repository) {
        this.repository = repository;
    }

    public ProfileResponse find(UserId userId) {
        return repository.byUserId(userId)
                .map(ProfileResponse::fromAggregate)
                .orElseThrow(() -> new ProfileNotExists(userId));
    }
}
