package com.application.administration.profiles.application.search_by_criteria;

import com.application.administration.profiles.application.ProfilesResponse;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.query.QueryHandler;
import com.application.shared.domain.criteria.FilterOrder;
import com.application.shared.domain.criteria.Filters;

@Service
public class ProfilesByCriteriaQueryHandler implements QueryHandler<ProfilesByCriteriaQuery, ProfilesResponse> {
    private final ProfilesByCriteriaSearcher searcher;

    public ProfilesByCriteriaQueryHandler(ProfilesByCriteriaSearcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public ProfilesResponse handle(ProfilesByCriteriaQuery query) {
        Filters filters = Filters.fromValues(query.filters());
        FilterOrder order   = FilterOrder.fromValues(query.orderBy(), query.orderType());

        return searcher.search(filters, order, query.limit(), query.offset());
    }
}
