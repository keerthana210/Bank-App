package com.keerthana.bank_app.controller;

import com.keerthana.bank_app.model.Admin;
import com.keerthana.bank_app.model.AdminLogin;
import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.service.AdminService;
import com.keerthana.bank_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/adminLogin")
    public ResponseStatusException adminLogin(@RequestBody AdminLogin login){
        System.out.println("32 "+login.getAdminId());
        if(adminService.adminExistById(login.getAdminId())){
            System.out.println("33 "+login.getAdminId());
            if(adminService.validateAdminCredentials(login.getAdminId(), login.getAdminPassword())){
                System.out.println("35 "+login.getAdminId());
                return new ResponseStatusException(HttpStatus.OK,"Logged In Successfully!");
            }
        }
        return new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid UserId or Password!");
    }
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getUserByAccNum")
    public User getUserById(@RequestParam String accNumber){
        List<User> users = userService.getAllUsers();
        for(User user:users){
            System.out.println(user.getAccNumber()+" "+accNumber);
            if((user.getAccNumber()).equals(accNumber)){
                return user;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @PostMapping("/NewAdmin")
    public ResponseStatusException addNewAdmin(@Valid @RequestBody Admin admin, BindingResult result){

        if (result.hasErrors()) {
            return new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Inputs!");
        }
        Admin registeredUser = adminService.addAdmin(admin);
        return new ResponseStatusException(HttpStatus.OK,"Admin Added Successfully!");

    }

}
