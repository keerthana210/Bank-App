package com.keerthana.bank_app.service;

import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.model.UserRegistration;
import com.keerthana.bank_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepo;

    public UserService(UserRepository userRepo,PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }
    public User saveUser(UserRegistration userRegistration){
        User user = new User();
        user.setAccNumber(userRegistration.getAccNumber());
        user.setAccHolderName(userRegistration.getAccHolderName());
        user.setAccHolderLocation(userRegistration.getAccHolderLocation());
        user.setContactNumber(userRegistration.getContactNumber());
        user.setEmailId(userRegistration.getEmailId());
        user.setAccBalance(userRegistration.getAccBalance());

        user.setAccPassword(passwordEncoder.encode(userRegistration.getAccPassword()));
        user.setTransactionPin(passwordEncoder.encode(userRegistration.getTransactionPin()));

        User userIdUpdate = userRepo.save(user);
        String customId = "HIRA" + String.format("%03d", userIdUpdate.getId());
        userIdUpdate.setUserId(customId);

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
        return passwordEncoder.matches(accPassword,user.getAccPassword());
    }

    public Optional<User> getUserByAccNumber(String accNumber) {
        return userRepo.findByAccNumber(accNumber);
    }

    public boolean userExistByAccountNumber(String accNumber) {
        return userRepo.existsByAccNumber(accNumber);
    }


    public User findUserByAccNumber(String loginId) {
      return userRepo.findUserByAccNumber(loginId);
    }

    public boolean existsByUserId(String loginId) {
        return userRepo.existsByUserId(loginId);
    }

    public User findUserByUserId(String loginId) {
        return userRepo.findUserByUserId(loginId);
    }


    public String getAccHolderNameByAccNumber(String name) {
        return userRepo.findUserByAccNumber(name).getAccHolderName();
    }
}
