package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.model.*;
import com.keerthana.bank_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
public class UserController {
    private UserService userService;
    private AuthenticationManager authManager;

    public UserController(UserService userService, @Qualifier("userAuthenticationManager") AuthenticationManager authManager) {
        this.userService = userService;
        this.authManager = authManager;
    }
    private String getLoggedInUserAccNumber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciples userDetails = (UserPrinciples) authentication.getPrincipal();
        return userDetails.getUsername();
    }
    @PostMapping("/login")
    public ResponseEntity<String> userLoginById(@RequestBody UserLogin login, HttpServletRequest request) {
        String loginId = login.getLoginId();
        String loginPassword = login.getAccPassword();

        if (loginId.isEmpty() || loginPassword.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login ID or Password cannot be empty");
        }

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, loginPassword)
        );

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            request.getSession(true)
                    .setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return ResponseEntity.ok("User logged in successfully!");
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials!");
    }


    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistration user, BindingResult result) {
        if (userService.existsByAccNumber(user.getAccNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account number already exists");
        }
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Inputs!");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("User Registered Successfully!");
    }


    @GetMapping("/view-profile")
    public ResponseEntity<ViewProfile> userProfile(){
        String userAccNumber = getLoggedInUserAccNumber();
        Optional<User> userOpt = userService.getUserByAccNumber(userAccNumber);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = userOpt.get();
        ViewProfile viewProfile = new ViewProfile(user.getAccHolderName(),user.getAccHolderLocation(), user.getUserId(),user.getContactNumber(), user.getEmailId(), user.getAccNumber());

        return ResponseEntity.ok(viewProfile);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UpdateProfile updateProfile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accNumber = authentication.getName();

        Optional<User> userOpt = userService.getUserByAccNumber(accNumber);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();

        user.setAccHolderName(updateProfile.getAccHolderName());
        user.setAccHolderLocation(updateProfile.getAccHolderLocation());
        user.setContactNumber(updateProfile.getContactNumber());
        user.setEmailId(updateProfile.getEmailId());

        userService.saveUser(user);

        return ResponseEntity.ok("Profile updated successfully");
    }
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // get session if exists
        if (session != null) {
            session.invalidate(); // destroy session
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "User logged out successfully");
        return ResponseEntity.ok(response);
    }

}
