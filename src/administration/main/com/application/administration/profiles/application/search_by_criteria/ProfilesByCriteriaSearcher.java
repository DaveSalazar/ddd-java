package com.application.administration.profiles.application.search_by_criteria;

import com.application.administration.profiles.application.ProfileResponse;
import com.application.administration.profiles.application.ProfilesResponse;
import com.application.administration.profiles.domain.ProfileRepository;
import com.application.shared.domain.Service;
import com.application.shared.domain.criteria.Criteria;
import com.application.shared.domain.criteria.FilterOrder;
import com.application.shared.domain.criteria.Filters;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfilesByCriteriaSearcher {

    private final ProfileRepository repository;

    public ProfilesByCriteriaSearcher(ProfileRepository repository) {
        this.repository = repository;
    }

    public ProfilesResponse search(
            Filters filters,
            FilterOrder order,
            Optional<Integer> limit,
            Optional<Integer> offset
    ) {
        Criteria criteria = new Criteria(filters, order, limit, offset);

        return new ProfilesResponse(
                repository.matching(criteria)
                        .stream()
                        .map(ProfileResponse::fromAggregate)
                        .collect(Collectors.toList())
        );
    }
}
