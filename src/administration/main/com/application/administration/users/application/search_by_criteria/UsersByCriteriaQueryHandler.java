package com.application.administration.users.application.search_by_criteria;

import com.application.administration.users.application.UsersResponse;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.query.QueryHandler;
import com.application.shared.domain.criteria.FilterOrder;
import com.application.shared.domain.criteria.Filters;

@Service
public class UsersByCriteriaQueryHandler implements QueryHandler<UsersByCriteriaQuery, UsersResponse> {

    private final UsersByCriteriaSearcher searcher;

    public UsersByCriteriaQueryHandler(UsersByCriteriaSearcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public UsersResponse handle(UsersByCriteriaQuery query) {
        Filters filters = Filters.fromValues(query.filters());
        FilterOrder order   = FilterOrder.fromValues(query.orderBy(), query.orderType());

        return searcher.search(filters, order, query.limit(), query.offset());
    }
}
