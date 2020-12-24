package com.application.administration.users.domain;


import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.criteria.Criteria;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> authenticate(UserEmail email);
    Optional<User> search(UserId id);
    List<User> searchAll();
    List<User> matching(Criteria criteria);
}
