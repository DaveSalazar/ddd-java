package com.application.administration.users.application.register;

import com.application.administration.profiles.application.save.ProfileCreator;
import com.application.administration.profiles.domain.ProfileFirstName;
import com.application.administration.profiles.domain.ProfileLastName;
import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.administration.users.domain.UserEmail;
import com.application.administration.users.domain.UserPassword;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.command.CommandHandler;

@Service
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand> {

    private UserRegister register;
    private ProfileCreator profileCreator;

    public RegisterUserCommandHandler(UserRegister register, ProfileCreator profileCreator) {
        this.register = register;
        this.profileCreator = profileCreator;
    }

    @Override
    public void handle(RegisterUserCommand command) {
        UserId userId = new UserId(command.id());
        UserEmail email = new UserEmail(command.email().toLowerCase());
        UserPassword password = new UserPassword(command.password());

        ProfileId profileId = new ProfileId(command.profileId());
        ProfileFirstName firstName = new ProfileFirstName(command.firstName());
        ProfileLastName lastName = new ProfileLastName(command.lastName());

        register.create(userId, email, password);
        profileCreator.save(profileId, userId, firstName, lastName);
    }
}
