package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.dao.UserDAO;
import com.ensta.myfilmlist.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

public class UserService implements UserDetailsService {
    private UserDAO userDAO;

    public UserService(UserDetails user) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findByUsername(username);
        System.err.println(user);
        if (user.isPresent()) {
            System.err.println("USERNAME = " + user.get().getUsername());
            System.err.println("PASSWORD = " + user.get().getPassword());
            return new org.springframework.security.core.userdetails.User(
                    user.get().getUsername(),
                    user.get().getPassword(),
                    new ArrayList<>()
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
