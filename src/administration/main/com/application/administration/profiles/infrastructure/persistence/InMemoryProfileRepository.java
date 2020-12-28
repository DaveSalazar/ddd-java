package com.application.administration.profiles.infrastructure.persistence;

import com.application.administration.profiles.domain.Profile;
import com.application.administration.profiles.domain.ProfileRepository;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.shared.domain.criteria.Criteria;
import com.application.shared.infrastructure.in_memory.InMemoryUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InMemoryProfileRepository implements ProfileRepository {

    private HashMap<String, Profile> profiles = new HashMap<>();

    @Override
    public void save(Profile profile) {
        profiles.put(profile.id().value(), profile);
    }

    @Override
    public Optional<Profile> search(ProfileId id) {
        return Optional.ofNullable(profiles.get(id.value()));
    }

    @Override
    public Optional<Profile> byUserId(UserId id) {
        return Optional.empty();
    }

    @Override
    public List<Profile> searchAll() {
        return new ArrayList<>(profiles.values());
    }

    @Override
    public List<Profile> matching(Criteria criteria) {
        InMemoryUtils<Profile> utils = new InMemoryUtils<>();
        return utils.criteria(new ArrayList<>(profiles.values()), criteria);
    }

}
