package org.ilestegor.lab1.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ilestegor.lab1.exception.exceptions.AccountLockedException;
import org.ilestegor.lab1.service.impl.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LockCheckFilter extends OncePerRequestFilter {
    @Autowired
    private LoginAttemptService loginAttemptService;

    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver resolver;

    @Value("${lock.check.method.name}")
    private String methodName;

    @Value("${lock.check.endpoint}")
    private String endpoint;

    @Value("${lock.check.field_name}")
    private String fieldName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isLogin(request)) {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            String username = extractUsername(cachedBodyHttpServletRequest.getCachedBody(), cachedBodyHttpServletRequest.getContentType());
            var unlocked = loginAttemptService.getUnlockAt(username);
            if (username != null && unlocked.isPresent()) {
                if (unlocked.get().isAfter(LocalDateTime.now())) {
                    resolver.resolveException(request, response, null, new AccountLockedException(unlocked.get()));
                    return;
                }

            }
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isLogin(HttpServletRequest req) {
        return methodName.equalsIgnoreCase(req.getMethod()) && endpoint.equals(req.getServletPath());
    }

    private String extractUsername(byte[] body, String contentType) throws IOException {
        if (contentType != null && contentType.contains("application/json") && body != null && body.length > 0) {
            return new ObjectMapper().readTree(body).path(fieldName).asText(null);
        }
        return null;
    }
}
