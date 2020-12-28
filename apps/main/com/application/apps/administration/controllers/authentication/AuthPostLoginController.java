package com.application.apps.administration.controllers.authentication;

import com.application.shared.domain.DomainError;
import com.application.shared.domain.bus.command.CommandBus;
import com.application.shared.domain.bus.query.QueryBus;
import com.application.shared.infrastructure.spring.ApiController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.application.apps.administration.utils.Constants.PREFIX;

@RestController
public class AuthPostLoginController  extends ApiController {

    public AuthPostLoginController(QueryBus queryBus, CommandBus commandBus) {
        super(queryBus, commandBus);
    }

    @PostMapping(PREFIX + "/auth/signup")
    public void run() {

    }
    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return null;
    }
}
