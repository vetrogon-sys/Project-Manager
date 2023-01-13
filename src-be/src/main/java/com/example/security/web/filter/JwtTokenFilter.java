package com.example.security.web.filter;

import com.example.security.service.TokenProvider;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String WRONG_OR_EMPTY_AUTHENTICATION_TOKEN_MESSAGE = "Wrong or empty authentication token found";
    public static final String BEARER_VALUE = "Bearer ";

    private final TokenProvider tokenProvider;

    public JwtTokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {
        resolveTokenFromRequest(httpServletRequest)
              .ifPresent(t -> authenticateToken(t, httpServletResponse));

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authenticateToken(String token, HttpServletResponse response) {
        if (tokenProvider.validateToken(token)) {
            Authentication auth = tokenProvider.getAuthenticationFromToken(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            invalidateSessionAndSendError(response);
        }
    }

    @SneakyThrows
    private void invalidateSessionAndSendError(HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        response.sendError(HttpStatus.UNAUTHORIZED.value(), WRONG_OR_EMPTY_AUTHENTICATION_TOKEN_MESSAGE);
    }

    private Optional<String> resolveTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith(BEARER_VALUE)) {
            return Optional.of(bearerToken.substring(BEARER_VALUE.length()));
        }
        return Optional.empty();
    }

}
