package org.ilestegor.lab1.service.impl;

import org.ilestegor.lab1.dto.JwtRequestDto;
import org.ilestegor.lab1.model.User;
import org.ilestegor.lab1.repository.UserRepository;
import org.ilestegor.lab1.service.UserService;
import org.ilestegor.lab1.utils.PasswordUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;

    public UserServiceImpl(UserRepository userRepository, PasswordUtils passwordUtils) {
        this.userRepository = userRepository;
        this.passwordUtils = passwordUtils;
    }

    @Override
    public User addUser(JwtRequestDto jwtRequestDto) {
        User user = new User();
        user.setUserName(jwtRequestDto.username());
        user.setPassword(passwordUtils.hashPassword(jwtRequestDto.password()));
        return userRepository.save(user);
    }

    @Override
    public boolean isUserExistsByUsername(String name) {
        return userRepository.existsUsersByUserName(name);
    }
}
