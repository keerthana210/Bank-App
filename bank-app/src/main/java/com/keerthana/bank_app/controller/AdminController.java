package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.model.Admin;
import com.keerthana.bank_app.model.AdminLogin;
import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.service.AdminService;
import com.keerthana.bank_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/admin/")
public class AdminController {

    private UserService userService;

    private AdminService adminService;

    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @PostMapping("/admin-login")
    public ResponseEntity<String > adminLogin(@RequestBody AdminLogin login){
        if(adminService.adminExistById(login.getAdminId())){
            if(adminService.validateAdminCredentials(login.getAdminId(), login.getAdminPassword())){
                return ResponseEntity.ok("Logged In Successfully!");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UserId or Password!");
    }
    @GetMapping("/all-users")
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

    @PostMapping("/NewAdmin")
    public ResponseEntity<String> addNewAdmin(@Valid @RequestBody Admin admin, BindingResult result){
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Inputs!");
        }
        Admin registeredUser = adminService.addAdmin(admin);
        return ResponseEntity.ok("Admin Added Successfully!");

    }

}
