package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.model.*;
import com.keerthana.bank_app.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
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
    private SecurityContextRepository securityContextRepository;

    public UserController(UserService userService, @Qualifier("userAuthenticationManager") AuthenticationManager authManager, SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.authManager = authManager;
        this.securityContextRepository = securityContextRepository;
    }

    private String getLoggedInUserAccNumber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciples userDetails = (UserPrinciples) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @PostMapping("/clear-session")
    public ResponseEntity<String> clearSession(HttpServletRequest request) {
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        return ResponseEntity.ok("Session cleared");
    }

    @GetMapping("/current")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getAccHolderNameByAccNumber(authentication.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLoginById(@RequestBody UserLogin login,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        String loginId = login.getLoginId();
        String loginPassword = login.getAccPassword();

        if (loginId == null || loginId.isBlank() || loginPassword == null || loginPassword.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login ID or Password cannot be empty");
        }
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, loginPassword)
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        return ResponseEntity.ok("User logged in successfully!");
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
        ViewProfile viewProfile = new ViewProfile(user.getAccHolderName(),user.getAccHolderLocation(),
                user.getUserId(),user.getContactNumber(), user.getEmailId(), user.getAccNumber());
        return ResponseEntity.ok(viewProfile);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UpdateProfile updateProfile,
                                                    BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMsg);
        }
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

    @GetMapping("/balance")
    public ResponseEntity<Double> balanceCheck() {
        String userAccNumber = getLoggedInUserAccNumber();

        return userService.getUserByAccNumber(userAccNumber)
                .map(user -> ResponseEntity.ok(user.getAccBalance()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found!"
                ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "User logged out successfully");
        return ResponseEntity.ok(response);
    }
}
