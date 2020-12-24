package com.application.administration.users.application.save;

import com.application.administration.users.domain.UserEmail;
import com.application.administration.users.domain.UserPassword;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.command.CommandHandler;

@Service
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {

    private UserCreator creator;

    public CreateUserCommandHandler(UserCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handle(CreateUserCommand command) {
       UserId id = new UserId(command.id());
       UserEmail email = new UserEmail(command.email().toLowerCase());
       UserPassword password = new UserPassword(command.password());

       creator.create(id, email, password);
    }
}
