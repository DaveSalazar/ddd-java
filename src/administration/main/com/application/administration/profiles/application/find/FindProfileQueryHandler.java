package com.application.administration.profiles.application.find;

import com.application.administration.profiles.application.ProfileResponse;
import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.query.QueryHandler;

@Service
public class FindProfileQueryHandler implements QueryHandler<FindProfileQuery, ProfileResponse> {

    private final ProfileFinder finder;

    public FindProfileQueryHandler(ProfileFinder finder) {
        this.finder = finder;
    }

    @Override
    public ProfileResponse handle(FindProfileQuery query) {
        return finder.find(new ProfileId(query.id()));
    }
}
