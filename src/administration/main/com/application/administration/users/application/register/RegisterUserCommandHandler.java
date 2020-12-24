package com.application.administration.users.application.register;

import com.application.administration.users.domain.UserEmail;
import com.application.administration.users.domain.UserPassword;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.command.CommandHandler;

@Service
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand> {

    private UserRegister register;

    public RegisterUserCommandHandler(UserRegister register) {
        this.register = register;
    }

    @Override
    public void handle(RegisterUserCommand command) {
        UserId id = new UserId(command.id());
        UserEmail email = new UserEmail(command.email().toLowerCase());
        UserPassword password = new UserPassword(command.password());
        register.create(id, email, password);
    }
}
