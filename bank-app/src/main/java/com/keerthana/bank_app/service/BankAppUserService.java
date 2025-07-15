package com.keerthana.bank_app.service;

import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.model.UserPrinciples;
import com.keerthana.bank_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAppUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String accNumber) throws UsernameNotFoundException {

        if(!userRepository.existsByAccNumber(accNumber)){
            System.out.println("bankapp issue ");
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not Found");
        }
        User user = userRepository.findUserByAccNumber(accNumber);
        return new UserPrinciples(user);
    }
}
