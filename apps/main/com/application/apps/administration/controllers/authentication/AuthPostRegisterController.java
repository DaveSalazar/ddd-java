package com.application.apps.administration.controllers.authentication;

import com.application.administration.users.application.register.RegisterUserCommand;
import com.application.apps.administration.Requests.RegisterRequest;
import com.application.apps.administration.utils.JwtUtil;
import com.application.shared.domain.DomainError;
import com.application.shared.domain.bus.command.CommandBus;
import com.application.shared.domain.bus.command.CommandHandlerExecutionError;
import com.application.shared.domain.bus.query.QueryBus;
import com.application.shared.infrastructure.config.Parameter;
import com.application.shared.infrastructure.config.ParameterNotExist;
import com.application.shared.infrastructure.spring.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;

import static com.application.apps.administration.utils.Constants.PREFIX;

@RestController
public class AuthPostRegisterController extends ApiController {

    @Autowired
    private JwtUtil jwtTokenUtil;
    private final Parameter param;

    public AuthPostRegisterController(QueryBus queryBus, CommandBus commandBus, Parameter param) {
        super(queryBus, commandBus);
        this.param = param;
    }

    @PostMapping(PREFIX + "/auth/signup")
    public ResponseEntity handle(@RequestBody RegisterRequest request)
        throws CommandHandlerExecutionError, ParameterNotExist {

        dispatch(new RegisterUserCommand(request.getId(), request.getEmail(), request.getPassword(),
            request.getFirstName(), request.getLastName()));

        final String jwt = jwtTokenUtil.generateToken(
            request.getEmail().toLowerCase(), request.getId(), param.get("CLIENT_FRONTEND_VERSION"));

        return ResponseEntity.ok().body(new HashMap<String, Serializable>() {{
            put("token", jwt);
            put("userId", request.getId());
            put("userEmail", request.getEmail());
            put("profile", new HashMap<String, Serializable>() {{
                put("profileId", request.getProfileId());
                put("firstName", request.getFirstName());
                put("lastName", request.getLastName());
            }});
        }});
    }
    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return null;
    }
}
