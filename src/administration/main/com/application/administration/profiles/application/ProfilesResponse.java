package com.application.administration.profiles.application;

import com.application.shared.domain.bus.query.Response;

import java.util.List;

public class ProfilesResponse implements Response {
    private final List<ProfileResponse> response;

    public ProfilesResponse(List<ProfileResponse> response) {
        this.response = response;
    }

    public List<ProfileResponse> response() {
        return response;
    }
}
