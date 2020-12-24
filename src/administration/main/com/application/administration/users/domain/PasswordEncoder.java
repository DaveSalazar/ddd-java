package com.application.administration.users.domain;

public interface PasswordEncoder {
    String encode(String password);
    Boolean isValid(String encodedPassword, String password);
}
