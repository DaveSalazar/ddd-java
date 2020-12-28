package com.application.administration.profiles.application.save;

import com.application.administration.profiles.domain.*;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.shared.domain.Service;
import com.application.shared.domain.bus.command.CommandHandler;

@Service
public class CreateProfileCommandHandler implements CommandHandler<CreateProfileCommand> {

    private final ProfileCreator creator;

    public CreateProfileCommandHandler(ProfileCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handle(CreateProfileCommand command) {
        ProfileId id = new ProfileId(command.id());
        UserId memberId = new UserId(command.memberId());
        ProfileFirstName firstName = new ProfileFirstName(command.firstName());
        ProfileLastName lastName = new ProfileLastName(command.lastName());

        creator.save(id, memberId, firstName, lastName);
    }
}
