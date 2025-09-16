package org.ilestegor.lab1.controller;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.ilestegor.lab1.dto.JwtRequestDto;
import org.ilestegor.lab1.dto.JwtResponseDto;
import org.ilestegor.lab1.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> register(@RequestBody @Valid JwtRequestDto jwtRequestDto, HttpServletResponse response) {
        return new ResponseEntity<>(authService.register(jwtRequestDto, response), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid JwtRequestDto jwtRequestDto, HttpServletResponse response) {
        return new ResponseEntity<>(authService.login(jwtRequestDto, response), HttpStatus.OK);
    }
}
