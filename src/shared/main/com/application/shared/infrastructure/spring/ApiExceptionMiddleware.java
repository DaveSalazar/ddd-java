package com.application.shared.infrastructure.spring;

import com.application.shared.domain.Utils;
import com.application.shared.domain.errors.ServerError;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.NestedServletException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public final class ApiExceptionMiddleware implements Filter {
    private RequestMappingHandlerMapping mapping;

    public ApiExceptionMiddleware(RequestMappingHandlerMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws ServletException {
        HttpServletRequest  httpRequest  = ((HttpServletRequest) request);
        HttpServletResponse httpResponse = ((HttpServletResponse) response);
        try {
            Object possibleController = null;
            Object handler = mapping.getHandler(httpRequest);
            if (handler != null) {
                possibleController = ((HandlerMethod) Objects.requireNonNull(mapping.getHandler(httpRequest))
                    .getHandler()).getBean();
            }
            try {
                chain.doFilter(request, response);
            } catch (NestedServletException exception) {
                if (possibleController instanceof ApiController) {
                    Utils.handleCustomError(response, httpResponse, (ApiController) possibleController, exception);
                }else {
                    Utils.handleCustomError(response, httpResponse, new ServerError(), 500);
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
