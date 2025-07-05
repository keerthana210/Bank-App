package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.repository.UserRepository;
import com.keerthana.bank_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/*
Used to control the activities of User alias Customer
* Manages login and registration
---->profile creation
* Retrieves user details
---->Provide profile details after login
* Manages transaction
---->transfer money
---->Withdraw
---->Deposit
*/

@RestController
@RequestMapping("/user/")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository=userRepository;
    }

    @RequestMapping("/")
    public String welcome(){
        return "Welcome to Bank!";
    }

    @GetMapping("/userDetail/{userId}/{accPassword}")
    public User getUserDetails( @PathVariable int userId, @PathVariable String accPassword){
        List<User> user = userService.getAllUsers();
        if(userId==1 && accPassword.equals("Keerthi8*"))
            return userService.getAllUsers().get(userId-1);
        return null;
    }
    
    @PostMapping("/userRegistration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getGlobalErrors());
        }
        User registeredUser = userRepository.save(user);
        return ResponseEntity.ok("User Registered Successfully!");
    }










}
