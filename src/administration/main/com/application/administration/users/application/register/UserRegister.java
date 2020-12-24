package com.application.administration.users.application.register;

import com.application.administration.users.domain.*;
import com.application.administration.users.infrastructure.encryption.AdministrationBcryptEncoder;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.event.EventBus;

@Service
public class UserRegister {

    private final UserRepository repository;
    private final EventBus eventBus;

    public UserRegister(UserRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    public void create(UserId id, UserEmail email, UserPassword password) {
        AdministrationBcryptEncoder encoder = new AdministrationBcryptEncoder();
        UserPassword pwd = new UserPassword(encoder.encode(password.value()));
        User user = new User(id, email, pwd);
        repository.save(user);
        //eventBus.publish(user.pullDomainEvents());
    }

}
