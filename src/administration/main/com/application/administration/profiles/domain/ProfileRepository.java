package com.application.administration.profiles.domain;

import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.criteria.Criteria;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {
    void save(Profile profile);
    Optional<Profile> search(ProfileId id);
    Optional<Profile> byUserId(UserId id);
    List<Profile> searchAll();
    List<Profile> matching(Criteria criteria);
}
