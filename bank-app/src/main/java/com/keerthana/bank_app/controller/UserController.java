package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.model.UserLogin;
import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLoginById(@RequestBody UserLogin login){
        if((login.getUserId()) != null){
            if(userService.userExistById(login.getUserId())){
                if(userService.validateUser(login.getUserId(),null, login.getAccPassword())){
                    return ResponseEntity.ok("Logged In Successfully!");
                }
            }
        }
        else if(login.getAccNumber()!=null){
            if(userService.userExistByAccountNumber(login.getAccNumber())){
                if(userService.validateUser(null,login.getAccNumber(),login.getAccPassword())){
                    return ResponseEntity.ok("Logged In Successfully!");
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Credentials!");
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (userService.existsByAccNumber(user.getAccNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account number already exists");
        }
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Inputs!");
        }

        User registeredUser = userService.saveUser(user);
        return ResponseEntity.ok("User Registered Successfully!");
    }
}
