package com.application.apps.administration.controllers.profile;

import com.application.administration.profiles.application.save.CreateProfileCommand;
import com.application.apps.administration.Requests.ProfileRequest;
import com.application.apps.administration.utils.JwtUtil;
import com.application.shared.domain.DomainError;
import com.application.shared.domain.bus.command.CommandBus;
import com.application.shared.domain.bus.command.CommandHandlerExecutionError;
import com.application.shared.domain.bus.query.QueryBus;
import com.application.shared.infrastructure.config.ParameterNotExist;
import com.application.shared.infrastructure.spring.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.application.apps.administration.utils.Constants.PREFIX;

@RestController
public class ProfilePutController extends ApiController {

    @Autowired
    private JwtUtil jwtTokenUtil;


    public ProfilePutController(QueryBus queryBus, CommandBus commandBus) {
        super(queryBus, commandBus);
    }

    @PutMapping(PREFIX + "/profile")
    public ResponseEntity handle(HttpServletRequest httpRequest, @RequestBody ProfileRequest request)
        throws CommandHandlerExecutionError, ParameterNotExist {

        final String authorizationHeader = httpRequest.getHeader("Authorization");
        final String token = authorizationHeader.substring(7);
        final String userId = jwtTokenUtil.getClaim(token, "user_id");

        dispatch(new CreateProfileCommand(request.getId(), request.getUserId(), request.getFirstName(), request.getLastName()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return null;
    }
}
