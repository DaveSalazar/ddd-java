package com.application.administration.users.infrastructure.encryption;

import com.application.administration.users.domain.PasswordEncoder;
import com.application.shared.domain.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AdministrationBcryptEncoder implements PasswordEncoder {

    private BCryptPasswordEncoder encoder;

    public AdministrationBcryptEncoder() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }

    @Override
    public Boolean isValid(String encodedPassword, String password) {
        return encoder.matches(password, encodedPassword);
    }
}
