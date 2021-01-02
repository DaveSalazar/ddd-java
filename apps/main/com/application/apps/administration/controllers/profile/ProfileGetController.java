package com.application.apps.administration.controllers.profile;

import com.application.administration.profiles.application.ProfileResponse;
import com.application.administration.profiles.application.find.ProfileFinder;
import com.application.administration.profiles.application.find_by_user_id.FindProfileByUserQuery;
import com.application.administration.users.application.UserResponse;
import com.application.administration.users.application.find.FindUserQuery;
import com.application.administration.users.application.login.AuthenticateUserQuery;
import com.application.apps.administration.Requests.RefreshTokenRequest;
import com.application.apps.administration.utils.JwtUtil;
import com.application.shared.domain.DomainError;
import com.application.shared.domain.bus.command.CommandBus;
import com.application.shared.domain.bus.command.CommandHandlerExecutionError;
import com.application.shared.domain.bus.query.QueryBus;
import com.application.shared.infrastructure.config.ParameterNotExist;
import com.application.shared.infrastructure.spring.ApiController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;

import static com.application.apps.administration.utils.Constants.PREFIX;

@RestController
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class ProfileGetController extends ApiController {

    @Autowired
    private JwtUtil jwtTokenUtil;

    public ProfileGetController(QueryBus queryBus, CommandBus commandBus) {
        super(queryBus, commandBus);
    }


    @GetMapping(PREFIX + "/profile")
    public ResponseEntity handle(HttpServletRequest httpRequest)
        throws CommandHandlerExecutionError, ParameterNotExist {

        final String authorizationHeader = httpRequest.getHeader("Authorization");
        final String token = authorizationHeader.substring(7);
        final String userId = jwtTokenUtil.getClaim(token, "user_id");

        UserResponse userResponse = ask(new FindUserQuery(userId));
        ProfileResponse profileResponse = ask(new FindProfileByUserQuery(userId));

        HashMap<String, Serializable> profile = new HashMap<String, Serializable>() {{
            put("id", profileResponse.id());
            put("userId", userResponse.id());
            put("userEmail", userResponse.email());
            put("firstName", profileResponse.firstName());
            put("lastName", profileResponse.lastName());
        }};

        return ResponseEntity.ok().body(new HashMap<String, Serializable>() {{
            put("profile", profile);
        }});
    }


    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return null;
    }
}
