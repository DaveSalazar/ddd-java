package com.application.administration.users.application.find;

import com.application.administration.shared.domain.identifiers.UserId;
import com.application.administration.users.application.UserResponse;
import com.application.administration.users.domain.UserNotExist;
import com.application.administration.users.domain.UserRepository;
import com.application.shared.domain.Service;

@Service
public class UserFinder {

    private final UserRepository repository;

    public UserFinder(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponse find(UserId id) throws UserNotExist {
        return repository.search(id)
                .map(UserResponse::fromAggregate)
                .orElseThrow(() -> new UserNotExist(id));
    }
}
