package com.application.apps.administration.middleware;

import com.application.apps.administration.utils.JwtUtil;
import com.application.shared.domain.Utils;
import com.application.shared.domain.errors.AppVersionMismatch;
import com.application.shared.domain.errors.ServerError;
import com.application.shared.domain.errors.TokenExpired;
import com.application.shared.infrastructure.config.ParameterNotExist;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthMiddleware implements Filter {

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        HttpServletResponse httpResponse = ((HttpServletResponse) response);

        final String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String username;
            String jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                if (!jwtUtil.validateVersion(jwt)) {
                    throw new AppVersionMismatch();
                }
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(jwt, username)) {
                        jwtUtil.createAuthentication(jwt).ifPresent(authentication ->
                                SecurityContextHolder.getContext().setAuthentication(authentication));
                    }
                }
            } catch (AppVersionMismatch throwable) {
                Utils.handleCustomError(response, httpResponse, throwable, 403);
                return;
            } catch (ExpiredJwtException expiredJwtException) {
                Utils.handleCustomError(response, httpResponse, new TokenExpired(), 403);
                return;
            } catch (ParameterNotExist | Exception throwable) {
                throwable.printStackTrace();
                Utils.handleCustomError(response, httpResponse, new ServerError(), 500);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
