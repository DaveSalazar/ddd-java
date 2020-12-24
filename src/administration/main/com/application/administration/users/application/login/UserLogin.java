package com.application.administration.users.application.login;

import com.application.administration.users.application.UserResponse;
import com.application.administration.users.domain.*;
import com.application.shared.domain.Service;

import java.util.Optional;

@Service
public class UserLogin {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserLogin(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public UserResponse authenticate(UserEmail email, UserPassword password) {
        Optional<User> auth = repository.authenticate(email);
        ensureUserExist(auth, email);
        ensureCredentialsAreValid(auth.get(), password);
        return auth.map(UserResponse::fromAggregate)
                .orElseThrow(() -> new UserEmailNotExist(email));
    }

    private void ensureUserExist(Optional<User> auth, UserEmail email) {
        if (!auth.isPresent()) {
            throw new UserNotExist(email);
        }
    }

    private void ensureCredentialsAreValid(User user, UserPassword password) {
        if (!encoder.isValid(user.password().value(), password.value())) {
            throw new InvalidAuthCredentials();
        }
    }
}
