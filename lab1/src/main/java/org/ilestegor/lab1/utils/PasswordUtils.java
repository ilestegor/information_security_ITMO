package org.ilestegor.lab1.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;

    public PasswordUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
