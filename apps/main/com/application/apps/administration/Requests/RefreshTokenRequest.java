package com.application.apps.administration.Requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("Model Login")
public class RefreshTokenRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private final String refreshToken;

    @NotNull
    @ApiModelProperty(required = true)
    private final String token;

    public RefreshTokenRequest(@NotNull String refreshToken, @NotNull String token) {
        this.refreshToken = refreshToken;
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getToken() {
        return token;
    }
}
