package com.practice.service;

import com.practice.entity.User;
import com.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    public void userRegister(User user);
    public String userLogin(String email, String password);
    public List<User> getAllUsers();
}
