package com.keerthana.bank_app.controller;


import com.keerthana.bank_app.enums.AdminAccessLevel;
import com.keerthana.bank_app.model.*;
import com.keerthana.bank_app.service.AdminService;
import com.keerthana.bank_app.service.TransactionService;
import com.keerthana.bank_app.service.UserService;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    private AuthenticationManager authManager;
    private UserService userService;
    private AdminService adminService;
    private TransactionService transactionService;

    public AdminController(UserService userService, AdminService adminService,
                           @Qualifier("adminAuthenticationManager") AuthenticationManager authManager,
                           TransactionService transactionService) {
        this.userService = userService;
        this.adminService = adminService;
        this.authManager = authManager;
        this.transactionService = transactionService;
    }

    @GetMapping("/access-level")
    public ResponseEntity<AdminAccessLevel> getAdminAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminId = authentication.getName();
        Admin admin = adminService.getAdminByAdminId(adminId);
        return ResponseEntity.ok(admin.getAccess());
    }

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@Valid @RequestBody AdminLogin login,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        String loginId = login.getLoginId();
        String loginPassword = login.getAdminPassword();
        if (loginId.isEmpty() || loginPassword.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login ID or Password cannot be empty");
        }
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, loginPassword)
        );
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession newSession = request.getSession(true);
            Admin admin = adminService.getAdminByAdminId(loginId);
            newSession.setAttribute("loggedAdmin", admin);
            new HttpSessionSecurityContextRepository()
                    .saveContext(SecurityContextHolder.getContext(), request, response);
            return ResponseEntity.ok("Admin logged in successfully!");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials!");
    }
    @PostMapping("/clear-session")
    public ResponseEntity<String> clearSession(HttpServletRequest request) {
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        return ResponseEntity.ok("Session cleared");
    }
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentAdmin(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/list-users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user-by-accNum")
    public ResponseEntity<User> getUserByAccNumber(@RequestParam String accNumber) {
        if (userService.existsByAccNumber(accNumber))
            return ResponseEntity.ok(userService.findUserByAccNumber(accNumber));
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
    }

    @GetMapping("/transactions-by-accNum")
    public ResponseEntity<List> getTransactionsByAccNumber(@RequestParam String accNumber) {
        if (userService.existsByAccNumber(accNumber)) {
            return ResponseEntity.ok(transactionService
                    .findBySenderAccNumOrReceiverAccNumOrderByIdDesc(accNumber, null));
        } else if (!userService.existsByAccNumber(accNumber)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account number not found");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction for Account number is empty!");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> defaultPasswordReset(@Valid @RequestBody AdminPasswordReset adminPasswordReset) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String adminId = auth.getName();
        if (adminService.adminExistByAdminId(adminId)) {
            if (adminService.validateAdminCredentials(adminId, adminPasswordReset.getOldAdminPassword())) {
                adminService.updateAdminPassword(adminId, adminPasswordReset.getNewAdminPassword());
                return ResponseEntity.ok("Password Changed Successfully!");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Id or Password!");
    }

    @PostMapping("/new-admin-creation")
    public ResponseEntity<String> addNewAdmin(@Valid @RequestBody AdminRegistration admin, BindingResult result) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean hasFullAccess = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("FULL_ACCESS"::equals);
        if (!hasFullAccess) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to create new admin");
        }
        if (adminService.existsByEmailId(admin.getEmailId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exists");
        }
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Inputs!");
        }
        adminService.saveAdmin(admin);
        return ResponseEntity.ok("Admin Added Successfully!");
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin logged out successfully");
        return ResponseEntity.ok(response);
    }

}
