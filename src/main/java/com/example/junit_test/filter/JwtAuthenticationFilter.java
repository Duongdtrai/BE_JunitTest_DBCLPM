package com.example.junit_test.filter;

import com.example.junit_test.modules.auth.service.JwtService;
import com.example.junit_test.modules.user.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // chua hieu spring boot sẽ tự động hiểu cái này nhu nào
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.warn("5: Start JWT Token Filter ");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        log.warn("Authorization Bearer " + authHeader);
        jwt = authHeader.substring(7);
        Integer countTokenExist = tokenRepository.countByToken(jwt);
        userEmail = jwtService.extractUsername(jwt);// to extract the userEmail from JWT Token (giai nen jwt)
        log.warn("User email " + authHeader);
        if (countTokenExist == 1 && userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            log.warn("UserDetails is:  " + userDetails);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // chua hieu doan nay
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                log.warn("GetAuthorities: " + userDetails.getAuthorities());
                log.warn("AuthToken 1: " + authToken);
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails((request))
                );
                log.warn("AuthToken 2: " + authToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        log.warn("Filter chain do filter");
        filterChain.doFilter(request, response);
    }
}
