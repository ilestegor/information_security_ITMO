package org.ilestegor.lab1.service;

import org.ilestegor.lab1.dto.JwtRequestDto;
import org.ilestegor.lab1.model.User;

public interface UserService {
    User addUser(JwtRequestDto jwtRequestDto);

    boolean isUserExistsByUsername(String name);
}
