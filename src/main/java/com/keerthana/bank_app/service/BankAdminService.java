package com.keerthana.bank_app.service;

import com.keerthana.bank_app.model.Admin;
import com.keerthana.bank_app.model.AdminPrinciples;
import com.keerthana.bank_app.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BankAdminService implements UserDetailsService {

    private AdminService adminService;

    private PasswordEncoder passwordEncoder;

    public BankAdminService(AdminService adminService, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {
        Admin admin = adminService.findByAdminId(adminId)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        return new AdminPrinciples(admin);
    }
}
