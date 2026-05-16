package com.practice.serviceImpl;

import com.practice.entity.User;
import com.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = getUserFromDb(email);

        return  org.springframework.security.core.userdetails.User.
                withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public User getUserFromDb(String email)
    {
        User user = userRepository.findByEmail(email);

        if(user == null)
        {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return user;
    }
}
