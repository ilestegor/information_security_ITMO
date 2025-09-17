package org.ilestegor.lab1.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.ilestegor.lab1.exception.exceptions.*;
import org.ilestegor.lab1.utils.ProblemUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GLobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request) {
        ProblemDetail body = ProblemUtils.pb(HttpStatus.BAD_REQUEST, "User already exists", "", request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UserNotfoundException.class)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(UserNotfoundException ex, HttpServletRequest request) {
        ProblemDetail body = ProblemUtils.pb(HttpStatus.BAD_REQUEST, "User with name" + ex.getMessage() + " was not found", "", request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DeadlineInPastException.class)
    public ResponseEntity<ProblemDetail> handleDeadlineInPastException(DeadlineInPastException ex, HttpServletRequest request) {
        ProblemDetail body = ProblemUtils.pb(HttpStatus.BAD_REQUEST, "Deadline in past", "Change deadline date", request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleBadCredentialsExceptions(BadCredentialsException ex, HttpServletRequest request) {
        ProblemDetail body = ProblemUtils.pb(HttpStatus.UNAUTHORIZED, "Login or password is incorrect", "", request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ProblemDetail> handleAccountLockedException(AccountLockedException ex, HttpServletRequest request) {
        ProblemDetail body = ProblemUtils.pb(HttpStatus.FORBIDDEN, "Account is locked", "Account will be unlocked at " + ex.getUnlockAt(), request);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ProblemDetail> handlerUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        ProblemDetail body = ProblemUtils.pb(HttpStatus.UNAUTHORIZED, "Not authorized", "", request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
