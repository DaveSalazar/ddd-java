package com.application.administration.users.application.find;


import com.application.administration.shared.domain.identifiers.UserId;
import com.application.administration.users.application.UserResponse;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.query.QueryHandler;

@Service
public class FindUserQueryHandler implements QueryHandler<FindUserQuery, UserResponse> {

    private final UserFinder finder;

    public FindUserQueryHandler(UserFinder finder) {
        this.finder = finder;
    }

    @Override
    public UserResponse handle(FindUserQuery query) {
        return finder.find(new UserId(query.id()));
    }
}
