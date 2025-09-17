package org.ilestegor.lab1.listeners;

import org.ilestegor.lab1.service.impl.LoginAttemptService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthEventListener {

    private final LoginAttemptService loginAttemptService;

    public AuthEventListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent ev) {
        String username = ev.getAuthentication().getName();
        loginAttemptService.resetFailedAttempts(username);
    }

    @EventListener
    public void onBadCredentials(AuthenticationFailureBadCredentialsEvent ev) {
        var principal = ev.getAuthentication().getPrincipal();
        String username = principal instanceof String ? (String) principal : null;
        if (username != null) loginAttemptService.increaseFailedAttempts(username);

    }
}
