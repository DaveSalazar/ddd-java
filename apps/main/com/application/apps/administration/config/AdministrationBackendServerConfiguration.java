package com.application.apps.administration.config;

import com.application.shared.domain.bus.command.CommandBus;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.application.shared.infrastructure.spring.ApiExceptionMiddleware;

@Configuration
public class AdministrationBackendServerConfiguration {
    private final CommandBus bus;
    private final RequestMappingHandlerMapping mapping;

    public AdministrationBackendServerConfiguration(CommandBus bus, RequestMappingHandlerMapping mapping) {
        this.bus = bus;
        this.mapping = mapping;
    }

    @Bean
    public FilterRegistrationBean<ApiExceptionMiddleware> apiExceptionMiddleware() {
        FilterRegistrationBean<ApiExceptionMiddleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiExceptionMiddleware(mapping));
        return registrationBean;
    }


    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
