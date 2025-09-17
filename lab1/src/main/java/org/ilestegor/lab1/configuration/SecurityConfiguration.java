package org.ilestegor.lab1.configuration;

import org.ilestegor.lab1.configuration.jwtConfig.JwtFilter;
import org.ilestegor.lab1.exception.DelegatingAuthEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    private final UserDetailsService userDetailsService;

    @Value("${bcrypt.password.strength}")
    private Integer passwordStrength;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(req ->
                {
                    req.requestMatchers("/api/auth/**").permitAll();
                    req.anyRequest().authenticated();
                })


                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(e ->
                e.authenticationEntryPoint(delegatingAuthEntryPoint()));
        http.addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(getLockCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(passwordStrength);
    }

    @Bean
    public JwtFilter getJwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public LockCheckFilter getLockCheckFilter() {
        return new LockCheckFilter();
    }

    @Bean
    public DelegatingAuthEntryPoint delegatingAuthEntryPoint() {
        return new DelegatingAuthEntryPoint();
    }

}
