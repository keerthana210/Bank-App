package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.configuration.SecurityConfig;
import com.keerthana.bank_app.enums.Role;
import com.keerthana.bank_app.model.Admin;
import com.keerthana.bank_app.model.AdminLogin;
import com.keerthana.bank_app.model.AdminPasswordReset;
import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.service.AdminService;
import com.keerthana.bank_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/admin/")
public class AdminController {

    @Autowired
    private SecurityConfig securityConfig;

    private UserService userService;

    private AdminService adminService;

    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<String > adminLogin(@RequestBody AdminLogin login){
        if(adminService.adminExistById(login.getAdminId())){
            if(adminService.validateAdminCredentials(login.getAdminId(), login.getAdminPassword())){
                return ResponseEntity.ok("Logged In Successfully!");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UserId or Password!");
    }
    @GetMapping("/list-users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user-by-accNum")
    public ResponseEntity<User> getUserByAccNumber(@RequestParam String accNumber){
        List<User> users = userService.getAllUsers();
        for(User user:users){
            if((user.getAccNumber()).equals(accNumber)){
                return ResponseEntity.ok().body(user);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> defaultPasswordReset(@RequestBody AdminPasswordReset adminPasswordReset){
        if(adminService.adminExistById(adminPasswordReset.getAdminId())){
            if(adminService.validateAdminCredentials(adminPasswordReset.getAdminId(),adminPasswordReset.getOldAdminPassword()))
            {
                adminService.updateAdminPassword(adminPasswordReset.getAdminId(),adminPasswordReset.getNewAdminPassword());
                return ResponseEntity.ok("Password Changed Successfully!");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Id or Password!");
    }

    @PostMapping("/new-admin-creation")
    public ResponseEntity<String> addNewAdmin(@Valid @RequestBody Admin admin, BindingResult result){
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Inputs!");
        }
        admin.setAdminPassword(securityConfig.passwordEncoder().encode(admin.getAdminPassword()));
        admin.setRole(Role.ROLE_ADMIN);
        Admin registeredUser = adminService.addAdmin(admin);
        return ResponseEntity.ok("Admin Added Successfully!");
    }

}
