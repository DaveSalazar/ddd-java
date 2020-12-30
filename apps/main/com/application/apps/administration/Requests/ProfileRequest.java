package com.application.apps.administration.Requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("Model Profile")
public class ProfileRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private final String id;

    @NotNull
    @ApiModelProperty(required = true)
    private final String userId;

    @NotNull
    @ApiModelProperty(required = true)
    private final String firstName;

    @NotNull
    @ApiModelProperty(required = true)
    private final String lastName;

    public ProfileRequest(@NotNull String id, @NotNull String userId, @NotNull String firstName, @NotNull String lastName) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
