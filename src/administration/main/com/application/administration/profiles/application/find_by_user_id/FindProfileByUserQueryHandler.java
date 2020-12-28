package com.application.administration.profiles.application.find_by_user_id;

import com.application.administration.profiles.application.ProfileResponse;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.query.QueryHandler;

@Service
public class FindProfileByUserQueryHandler implements QueryHandler<FindProfileByUserQuery, ProfileResponse> {

    private final ProfileByUserFinder finder;

    public FindProfileByUserQueryHandler(ProfileByUserFinder finder) {
        this.finder = finder;
    }

    @Override
    public ProfileResponse handle(FindProfileByUserQuery query) {
        return finder.find(new UserId(query.id()));
    }
}
