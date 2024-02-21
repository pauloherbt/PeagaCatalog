package com.example.peagacatalog.configs;

import com.example.peagacatalog.repositories.UserRepository;
import com.example.peagacatalog.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if(token==null){
            filterChain.doFilter(request,response);
            return;
        }

        String user = tokenService.validateToken(token);
        UserDetails userDetails = userRepository.findByEmail(user).orElseThrow();
        Authentication auth = UsernamePasswordAuthenticationToken.authenticated(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request,response);
    }

    private String extractToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        //Authorization : Bearer (token)
        return header!=null ? header.substring(7):null;
    }
}
