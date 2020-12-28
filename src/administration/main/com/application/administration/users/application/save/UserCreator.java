package com.application.administration.users.application.save;

import com.application.administration.users.domain.*;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.event.EventBus;

@Service
public class UserCreator {

    private final UserRepository repository;
    private final EventBus eventBus;
    private final PasswordEncoder encoder;

    public UserCreator(UserRepository repository, EventBus eventBus, PasswordEncoder encoder) {
        this.repository = repository;
        this.eventBus = eventBus;
        this.encoder = encoder;
    }

    public void create(UserId id, UserEmail email, UserPassword password) {
        UserPassword pwd = new UserPassword(encoder.encode(password.value()));
        User user = new User(id, email, pwd);
        repository.save(user);
        eventBus.publish(user.pullDomainEvents());
    }
}
