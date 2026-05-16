package com.practice.serviceImpl;

import com.practice.entity.User;
import com.practice.repository.UserRepository;
import com.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void userRegister(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String userLogin(String email, String password) {

        User user = userRepository.findByEmail(email);
        if( user!=null && user.getPassword().equals(password))
        return "User Login SuccessFully !!";
        else
        return "User Credentials are incorrect !!";
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
