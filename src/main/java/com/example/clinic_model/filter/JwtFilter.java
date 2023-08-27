package com.example.clinic_model.filter;

import com.example.clinic_model.model.enums.RoleEnum;
import com.example.clinic_model.service.AuthenticationService;
import com.example.clinic_model.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Service
@Slf4j
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = jwtUtil.extractTokenFromRequest(request);
        if (Objects.nonNull(jwtToken)) {
            try {
                String username = jwtUtil.getUsernameFromToken(jwtToken);
                RoleEnum role = jwtUtil.getRoleFromToken(jwtToken);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = this.authenticationService.getUsernamePasswordAuthenticationToken(username, role);

                if (jwtUtil.validateToken(jwtToken, usernamePasswordAuthenticationToken.getName())) {
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException
                     | IllegalArgumentException | UsernameNotFoundException ex) {
                log.error("Authentication Error : {}", ex.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
