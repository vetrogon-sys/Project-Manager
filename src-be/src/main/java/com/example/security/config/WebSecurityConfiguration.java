package com.example.security.config;

import com.example.security.service.TokenProvider;
import com.example.security.web.filter.CustomAccessDeniedHandler;
import com.example.security.web.filter.JwtTokenFilterConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
      prePostEnabled = true,
      jsr250Enabled = true
)
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
              .csrf()
              .disable();

        http
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
              .authorizeRequests()
              .antMatchers("/auth/signin").permitAll()
              .antMatchers("/auth/signup").permitAll()
              .anyRequest().authenticated();

        http
              .exceptionHandling()
        .accessDeniedHandler(new CustomAccessDeniedHandler());

        http.apply(new JwtTokenFilterConfigurer(tokenProvider));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
