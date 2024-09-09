package com.Library_Management_System.security.authentication;

import com.Library_Management_System.security.authentication.filters.TokenFilter;
import com.Library_Management_System.security.authentication.providers.TokenProvider;
import com.Library_Management_System.security.authentication.providers.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final TokenProvider tokenProvider;
    private final TokenFilter tokenFilter;

    public AuthenticationConfig(@Lazy UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider,
                                @Lazy TokenProvider tokenProvider, @Lazy TokenFilter tokenFilter){

        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
        this.tokenFilter = tokenFilter;
        this.tokenProvider = tokenProvider;
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(tokenProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(getDisabledUrlPaths());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .addFilterAt(tokenFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/user/addUser").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                                .anyRequest().authenticated())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String[] getDisabledUrlPaths() {
        return new String[]{"/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/v2/api-docs/**"
        };
    }
}


