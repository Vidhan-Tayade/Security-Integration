package com.practice.security;

import com.practice.serviceImpl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if( token!=null && !token.isEmpty() )
        {
            token = token.substring(7);
            //token.split("\\.").length==3
            if(token!=null && !token.isEmpty())
            {
                String username=jwtUtil.extractToken(token);
                if( username!=null && !username.isEmpty() )
                {
                    UserDetails userdetail = userDetailsService.loadUserByUsername(username);
                    if( userdetail!=null)
                    {
                        SecurityContextHolder.getContext().setAuthentication
                                (new UsernamePasswordAuthenticationToken(userdetail.getUsername(),userdetail.getPassword(),userdetail.getAuthorities()));
                    }
                }
            }
        }

        //method of FilterChain
        filterChain.doFilter(request, response);
    }
}
