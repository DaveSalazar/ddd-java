package com.application.administration.users.application.save;

import com.application.administration.users.domain.*;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.event.EventBus;

@Service
public class UserCreator {

    private final UserRepository repository;
    private final EventBus eventBus;

    public UserCreator(UserRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    public void create(UserId id, UserEmail email, UserPassword password) {
        User user = new User(id, email, password);
        repository.save(user);
        eventBus.publish(user.pullDomainEvents());
    }
}
