package com.keerthana.bank_app.service;

import com.keerthana.bank_app.configuration.SecurityConfig;
import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private SecurityConfig securityConfig;
    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User saveUser(User user){
        user.setAccPassword(securityConfig.passwordEncoder().encode(user.getAccPassword()));
        user.setTransactionPin(securityConfig.passwordEncoder().encode(user.getTransactionPin()));
        return userRepo.save(user);
    }

    public boolean existsByAccNumber(String accNumber) {
        return userRepo.existsByAccNumber(accNumber);
    }

    public boolean userExistById(long userId) {
        return userRepo.existsById(userId);
    }

    public User getUserById(long userId){
        return userRepo.getReferenceById(userId);
    }

    public boolean validateUser(Long userId,String accNumber, String accPassword) {
        User user;
        if(userId!=null){
            user = getUserById(userId);
        }
        else{
            user = userRepo.findUserByAccNumber(accNumber);
        }
        return securityConfig.passwordEncoder().matches(accPassword,user.getAccPassword());
    }

    public Optional<User> getUserByAccNumber(String accNumber) {
        return userRepo.findByAccNumber(accNumber);
    }

    public boolean userExistByAccountNumber(String accNumber) {
        return userRepo.existsByAccNumber(accNumber);
    }
}
