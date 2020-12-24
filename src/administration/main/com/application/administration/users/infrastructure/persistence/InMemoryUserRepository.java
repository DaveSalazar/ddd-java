package com.application.administration.users.infrastructure.persistence;

import com.application.administration.users.domain.User;
import com.application.administration.users.domain.UserEmail;
import com.application.administration.users.domain.UserRepository;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.criteria.Criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class InMemoryUserRepository implements UserRepository {

    private HashMap<String, User> users = new HashMap<>();


    @Override
    public void save(User user) {
        users.put(user.id().value(), user);
    }

    @Override
    public Optional<User> authenticate(UserEmail email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> search(UserId id) {
        return Optional.ofNullable(users.get(id.value()));
    }

    @Override
    public List<User> searchAll() {
        return null;
    }

    @Override
    public List<User> matching(Criteria criteria) {
        return null;
    }
}
