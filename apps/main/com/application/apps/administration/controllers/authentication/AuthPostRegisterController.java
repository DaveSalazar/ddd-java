package com.application.apps.administration.controllers.authentication;

import com.application.administration.users.application.register.RegisterUserCommand;
import com.application.administration.users.domain.MemberEmailAlreadyExists;
import com.application.administration.users.domain.UserNotExists;
import com.application.apps.administration.Requests.RegisterRequest;
import com.application.apps.administration.utils.JwtUtil;
import com.application.shared.domain.DomainError;
import com.application.shared.domain.bus.command.CommandBus;
import com.application.shared.domain.bus.command.CommandHandlerExecutionError;
import com.application.shared.domain.bus.query.QueryBus;
import com.application.shared.domain.errors.ServerError;
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

    @PostMapping(PREFIX + "/auth/register")
    public ResponseEntity handle(@RequestBody RegisterRequest request)
        throws CommandHandlerExecutionError, ParameterNotExist {

        dispatch(new RegisterUserCommand(request.getId(), request.getProfileId(), request.getEmail(), request.getPassword(),
            request.getFirstName(), request.getLastName()));

        final String token = jwtTokenUtil.generateToken(
            request.getEmail().toLowerCase(), request.getId(), param.get("ADMINISTRATION_FRONTEND_VERSION"));
        final String refreshToken = jwtTokenUtil.generateRefreshToken(
            request.getEmail().toLowerCase(), request.getId(), param.get("ADMINISTRATION_FRONTEND_VERSION"));

        return ResponseEntity.ok().body(new HashMap<String, Serializable>() {{
            put("token", token);
            put("refreshToken", refreshToken);
            put("profile", new HashMap<String, Serializable>() {{
                put("userId", request.getId());
                put("profileId", request.getProfileId());
                put("userEmail", request.getEmail());
                put("firstName", request.getFirstName());
                put("lastName", request.getLastName());
            }});
        }});
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return new HashMap<Class<? extends DomainError>, HttpStatus>() {{
            put(MemberEmailAlreadyExists.class, HttpStatus.BAD_REQUEST);
        }};
    }
}
