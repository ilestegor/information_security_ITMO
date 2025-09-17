package org.ilestegor.lab1.configuration;

import org.ilestegor.lab1.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements ExtendedUserDetails {
    private final Long id;

    private final String username;

    private final String password;

    private final boolean isAccountNonLocked;

    public CustomUserDetails(User user) {
        this.id = user.getUserId();
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.isAccountNonLocked = user.isAccountNonLocked();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }
}
