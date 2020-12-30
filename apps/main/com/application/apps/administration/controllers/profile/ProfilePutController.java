package com.application.apps.administration.controllers.profile;

import com.application.administration.profiles.application.save.CreateProfileCommand;
import com.application.apps.administration.Requests.ProfileRequest;
import com.application.shared.domain.DomainError;
import com.application.shared.domain.bus.command.CommandBus;
import com.application.shared.domain.bus.command.CommandHandlerExecutionError;
import com.application.shared.domain.bus.query.QueryBus;
import com.application.shared.infrastructure.spring.ApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.application.apps.administration.utils.Constants.PREFIX;

@RestController
public class ProfilePutController extends ApiController {

    public ProfilePutController(QueryBus queryBus, CommandBus commandBus) {
        super(queryBus, commandBus);
    }

    @PutMapping(PREFIX + "/profile")
    public ResponseEntity handle(@RequestBody ProfileRequest request)
        throws CommandHandlerExecutionError {

        dispatch(new CreateProfileCommand(request.getId(), request.getUserId(), request.getFirstName(), request.getLastName()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return null;
    }
}
