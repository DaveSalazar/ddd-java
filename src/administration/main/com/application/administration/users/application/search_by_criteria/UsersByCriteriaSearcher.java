package com.application.administration.users.application.search_by_criteria;

import com.application.administration.users.application.UserResponse;
import com.application.administration.users.application.UsersResponse;
import com.application.administration.users.domain.UserRepository;
import com.application.shared.domain.Service;
import com.application.shared.domain.criteria.Criteria;
import com.application.shared.domain.criteria.FilterOrder;
import com.application.shared.domain.criteria.Filters;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersByCriteriaSearcher {

    private final UserRepository repository;

    public UsersByCriteriaSearcher(UserRepository repository) {
        this.repository = repository;
    }

    public UsersResponse search(
            Filters filters,
            FilterOrder order,
            Optional<Integer> limit,
            Optional<Integer> offset
    ) {
        Criteria criteria = new Criteria(filters, order, limit, offset);

        return new UsersResponse(
                repository.matching(criteria)
                        .stream()
                        .map(UserResponse::fromAggregate)
                        .collect(Collectors.toList())
        );
    }
}
