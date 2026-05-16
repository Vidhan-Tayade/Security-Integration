package com.practice.controller;

import com.practice.entity.User;
import com.practice.security.JwtUtil;
import com.practice.service.UserService;
import com.practice.serviceImpl.AuthResponse;
import com.practice.serviceImpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user)
    {
        userService.userRegister(user);
        return ResponseEntity.ok("User Registered Successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestParam("email") String email, @RequestParam("password") String password)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));

        //return userService.userLogin(email, password) + "\n" + jwtUtil.generateToken(email);
    }

    @GetMapping("/all")
    public List<User> allUsers()
    {
        return userService.getAllUsers();
    }
}
