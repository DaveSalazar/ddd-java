package com.application.administration.users.domain;

import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.AggregateRoot;
import com.application.shared.domain.events.UserCreatedDomainEvent;

import java.util.Objects;

public final class User extends AggregateRoot {

    private final UserId id;
    private final UserEmail email;
    private final UserPassword password;

    public User(UserId id, UserEmail email, UserPassword password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User() {
        this.id = null;
        this.email = null;
        this.password = null;
    }

    public static User create(UserId id, UserEmail email, UserPassword password) {
        User user = new User(id, email, password);
        return user;
    }

    public UserId id() {
        return id;
    }

    public UserEmail email() {
        return email;
    }

    public UserPassword password() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
