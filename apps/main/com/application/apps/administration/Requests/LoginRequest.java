package com.application.apps.administration.Requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("Model Login")
public class LoginRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private final String email;

    @NotNull
    @ApiModelProperty(required = true)
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
