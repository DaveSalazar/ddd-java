package com.application.apps.administration.controllers.authentication;

import com.application.administration.profiles.application.ProfileResponse;
import com.application.administration.profiles.application.find_by_user_id.FindProfileByUserQuery;
import com.application.administration.users.application.UserResponse;
import com.application.administration.users.application.login.AuthenticateUserQuery;
import com.application.administration.users.domain.UserNotExists;
import com.application.apps.administration.Requests.LoginRequest;
import com.application.apps.administration.utils.JwtUtil;
import com.application.shared.domain.DomainError;
import com.application.shared.domain.bus.command.CommandBus;
import com.application.shared.domain.bus.command.CommandHandlerExecutionError;
import com.application.shared.domain.bus.query.QueryBus;
import com.application.shared.domain.errors.ServerError;
import com.application.shared.infrastructure.config.Parameter;
import com.application.shared.infrastructure.config.ParameterNotExist;
import com.application.shared.infrastructure.spring.ApiController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class AuthPutLoginController extends ApiController {

    @Autowired
    private JwtUtil jwtTokenUtil;

    private final Parameter param;

    public AuthPutLoginController(QueryBus queryBus, CommandBus commandBus, Parameter param) {
        super(queryBus, commandBus);
        this.param = param;
    }

    @PostMapping(PREFIX + "/auth/login")
    public ResponseEntity handle(@RequestBody LoginRequest request)
        throws CommandHandlerExecutionError, ParameterNotExist {

        UserResponse userResponse = ask(new AuthenticateUserQuery(request.getEmail(), request.getPassword()));
        ProfileResponse profileResponse = ask(new FindProfileByUserQuery(userResponse.id()));

        HashMap<String, Serializable> profile = new HashMap<String, Serializable>() {{
            put("id", profileResponse.id());
            put("userId", userResponse.id());
            put("userEmail", userResponse.email());
            put("firstName", profileResponse.firstName());
            put("lastName", profileResponse.lastName());
        }};

        final String jwt = jwtTokenUtil.generateToken(
            request.getEmail().toLowerCase(),
            userResponse.id(),
            param.get("ADMINISTRATION_FRONTEND_VERSION")
        );

        return ResponseEntity.ok().body(new HashMap<String, Serializable>() {{
            put("token", jwt);
            put("profile", profile);
        }});
    }


    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return new HashMap<Class<? extends DomainError>, HttpStatus>() {{
            put(UserNotExists.class, HttpStatus.NOT_FOUND);
            put(ServerError.class, HttpStatus.INTERNAL_SERVER_ERROR);
        }};
    }
}
