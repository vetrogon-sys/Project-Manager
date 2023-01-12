package com.example.security.web.filter;

import com.example.exeptions.WrongAuthenticationTokenException;
import com.example.security.service.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String WRONG_OR_EMPTY_AUTHENTICATION_TOKEN_MESSAGE = "Wrong or empty authentication token found";
    public static final String INVALID_JWT_TOKEN_MESSAGE = "JWT token is invalid.";
    public static final String BEARER_VALUE = "Bearer ";

    private final TokenProvider tokenProvider;

    public JwtTokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveTokenFromRequest(httpServletRequest)
              .orElseThrow(() -> new WrongAuthenticationTokenException(WRONG_OR_EMPTY_AUTHENTICATION_TOKEN_MESSAGE));

        if (!tokenProvider.validateToken(token)) {
            SecurityContextHolder.clearContext();
            httpServletResponse
                  .sendError(HttpStatus.UNAUTHORIZED.value(), INVALID_JWT_TOKEN_MESSAGE);
            return;
        }

        Authentication auth = tokenProvider.getAuthenticationFromToken(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Optional<String> resolveTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith(BEARER_VALUE)) {
            return Optional.of(bearerToken.substring(BEARER_VALUE.length()));
        }
        return Optional.empty();
    }

}
