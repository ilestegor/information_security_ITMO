package org.ilestegor.lab1.configuration;

import org.springframework.security.core.userdetails.UserDetails;

public interface ExtendedUserDetails extends UserDetails {
    Long getId();
}
