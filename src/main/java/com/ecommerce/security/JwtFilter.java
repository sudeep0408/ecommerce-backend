package com.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain chain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        System.out.println("🚀 Token received: " + token); 
        String requestPath = request.getServletPath();
        System.out.println("🚀 Request received: " + requestPath); 
       
        if (requestPath.startsWith("/auth/register")) {
            chain.doFilter(request, response);
            return;
        }
        System.out.println("Current Authentication: " + SecurityContextHolder.getContext().getAuthentication());
        if (token != null && jwtUtils.validateToken(token) 
                && SecurityContextHolder.getContext().getAuthentication() == null) {
        	System.out.println("🚀 Token received: " + token); 
            String username = jwtUtils.extractUsername(token);
            System.out.println("🚀 Username received: " + username); 
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(token)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        System.out.println("🔒 Checking JWT for: " + requestPath);
        chain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7).trim();
        }
        return null;
    }
}
