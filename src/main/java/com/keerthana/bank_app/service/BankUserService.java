package com.keerthana.bank_app.service;

import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.model.UserPrinciples;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BankUserService implements UserDetailsService {

    private UserService userService;
    public BankUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = null;

        if (userService.existsByAccNumber(loginId)) {
            user = userService.findUserByAccNumber(loginId);
        }
        else if (userService.existsByUserId(loginId)) {
            user = userService.findUserByUserId(loginId);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found with accNumber or userId: " + loginId);
        }

        return new UserPrinciples(user);
    }
}
