package com.application.apps.administration.controllers.authentication;

import com.application.shared.domain.DomainError;
import com.application.shared.domain.bus.command.CommandBus;
import com.application.shared.domain.bus.query.QueryBus;
import com.application.shared.infrastructure.spring.ApiController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class AuthPostLoginController  extends ApiController {

    public AuthPostLoginController(QueryBus queryBus, CommandBus commandBus) {
        super(queryBus, commandBus);
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return null;
    }
}
