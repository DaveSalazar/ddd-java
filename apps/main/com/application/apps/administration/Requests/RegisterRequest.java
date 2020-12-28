package com.application.apps.administration.Requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("Model Register")
public class RegisterRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private final String id;

    @NotNull
    @ApiModelProperty(required = true)
    private final String email;

    @NotNull
    @ApiModelProperty(required = true)
    private final String password;

    @NotNull
    @ApiModelProperty(required = true)
    private final String profileId;

    @NotNull
    @ApiModelProperty(required = true)
    private final String firstName;

    @NotNull
    @ApiModelProperty(required = true)
    private final String lastName;

    public RegisterRequest(@NotNull String id, @NotNull String email, @NotNull String password, @NotNull String profileId,
                           @NotNull String firstName, @NotNull String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileId() {
        return profileId;
    }
}
