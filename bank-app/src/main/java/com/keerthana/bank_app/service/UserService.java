package com.keerthana.bank_app.service;

import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User saveUser(User user){
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

    public boolean validateUserCredentials(long userId, String accPassword) {
        User user = getUserById(userId);
        return user.getAccPassword().equals(accPassword);
    }

    public Optional<User> getUserByAccNumber(String accNumber) {
        return userRepo.findByAccNumber(accNumber);
    }

}
