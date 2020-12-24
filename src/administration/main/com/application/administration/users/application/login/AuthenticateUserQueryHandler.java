package com.application.administration.users.application.login;

import com.application.administration.users.application.UserResponse;
import com.application.administration.users.domain.UserEmail;
import com.application.administration.users.domain.UserPassword;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.query.QueryHandler;

@Service
public class AuthenticateUserQueryHandler implements QueryHandler<AuthenticateUserQuery, UserResponse> {

    private UserLogin authenticator;

    public AuthenticateUserQueryHandler(UserLogin authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public UserResponse handle(AuthenticateUserQuery command) {
       return authenticator.authenticate(new UserEmail(command.email().toLowerCase()), new UserPassword(command.password()));
    }
}
