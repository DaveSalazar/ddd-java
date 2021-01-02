package com.application.apps.administration.controllers.authentication;

import com.application.administration.users.application.UserResponse;
import com.application.administration.users.application.find.FindUserQuery;
import com.application.apps.administration.Requests.RefreshTokenRequest;
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
public class AuthRefreshTokenController extends ApiController {

    @Autowired
    private JwtUtil jwtTokenUtil;

    private final Parameter param;

    public AuthRefreshTokenController(QueryBus queryBus, CommandBus commandBus, Parameter param) {
        super(queryBus, commandBus);
        this.param = param;
    }

    @PostMapping(PREFIX + "/auth/refresh")
    public ResponseEntity handle(@RequestBody RefreshTokenRequest request)
        throws CommandHandlerExecutionError, ParameterNotExist {

        final String token = request.getToken();
        final String userId = jwtTokenUtil.getClaim(token, "user_id");
        UserResponse userResponse = ask(new FindUserQuery(userId));
        final String newToken = jwtTokenUtil.generateToken(
            userResponse.email().toLowerCase(), userId, param.get("ADMINISTRATION_FRONTEND_VERSION"));

        final String refreshToken = jwtTokenUtil.generateRefreshToken(
            userResponse.email().toLowerCase(), userId, param.get("ADMINISTRATION_FRONTEND_VERSION"));

        return ResponseEntity.ok().body(new HashMap<String, Serializable>() {{
            put("token", newToken);
            put("refreshToken", refreshToken);
        }});
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return null;
    }
}
