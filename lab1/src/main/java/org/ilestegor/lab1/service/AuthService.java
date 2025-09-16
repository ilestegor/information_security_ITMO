package org.ilestegor.lab1.service;

import jakarta.servlet.http.HttpServletResponse;
import org.ilestegor.lab1.dto.JwtRequestDto;
import org.ilestegor.lab1.dto.JwtResponseDto;

public interface AuthService {

    JwtResponseDto register(JwtRequestDto jwtRequestDto, HttpServletResponse response);

    JwtResponseDto login(JwtRequestDto jwtRequestDto, HttpServletResponse response);
}
