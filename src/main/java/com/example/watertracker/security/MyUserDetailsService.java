package com.example.watertracker.security;

import com.example.watertracker.db.User;
import com.example.watertracker.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

/*
 * Implementation of UserDetailsService, needed for spring security
 * authentication, authorization etc...
 */
public class MyUserDetailsService implements UserDetailsService {

    // Actual 'users' table
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(user);
    }


}
