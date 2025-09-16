package org.ilestegor.lab1.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.ilestegor.lab1.configuration.jwtConfig.JwtService;
import org.ilestegor.lab1.dto.JwtRequestDto;
import org.ilestegor.lab1.dto.JwtResponseDto;
import org.ilestegor.lab1.exception.exceptions.UserAlreadyExistsException;
import org.ilestegor.lab1.service.AuthService;
import org.ilestegor.lab1.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${jwt.bearer}")
    private String BEARER_TOKEN;
    @Value("${jwt.access-ttl}")
    private Duration tokenTtl;

    public AuthServiceImpl(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public JwtResponseDto register(JwtRequestDto jwtRequestDto, HttpServletResponse response) {
        if (userService.isUserExistsByUsername(jwtRequestDto.username())) {
            throw new UserAlreadyExistsException();
        }
        userService.addUser(jwtRequestDto);
        return login(jwtRequestDto, response);
    }

    @Override
    public JwtResponseDto login(JwtRequestDto jwtRequestDto, HttpServletResponse response) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDto.username(), jwtRequestDto.password()));
        } catch (AuthenticationException ex) {
            throw new org.ilestegor.lab1.exception.exceptions.AuthenticationException();
        }

        if (auth.isAuthenticated()) {
            String jwt = jwtService.generateAccessToken(auth);
            Cookie cookie = new Cookie(BEARER_TOKEN, jwt);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge((int) tokenTtl.toSeconds());
            cookie.setPath("/");
            response.addCookie(cookie);
            return new JwtResponseDto(jwtService.getClaimsFromToken(jwt).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), "JWT", true);
        }
        return new JwtResponseDto(null, "", false);
    }
}
