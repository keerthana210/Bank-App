package com.keerthana.bank_app.service;

import com.keerthana.bank_app.enums.Role;
import com.keerthana.bank_app.model.Admin;
import com.keerthana.bank_app.model.AdminRegistration;
import com.keerthana.bank_app.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin saveAdmin(AdminRegistration adminRegistration){
        Admin admin = new Admin();
        admin.setAdminName(adminRegistration.getAdminName());
        admin.setEmailId(adminRegistration.getEmailId());
        admin.setAccess(adminRegistration.getAccess());
        admin.setContactNumber(adminRegistration.getContactNumber());
        admin.setAdminPassword(adminRegistration.getAdminPassword());
        admin.setRole(Role.ADMIN);
        Admin idUpdate = saveAdmin(admin);
        idUpdate.setAdminId( "HIRA_AD" + String.format("%03d", idUpdate.getId()));
        return adminRepository.save(idUpdate);
    }
    public Admin saveAdmin(Admin admin){

        if (!admin.getAdminPassword().startsWith("$2a$")) {
            admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
        }
        return adminRepository.save(admin);
    }

    public Admin getAdminByAdminId(String adminId){
        return adminRepository.getAdminByAdminId(adminId);
    }
    public boolean adminExistByAdminId(String adminId) {
        return adminRepository.existsByAdminId(adminId);
    }

    public boolean validateAdminCredentials(String adminId, String adminPassword) {
        System.out.println(adminId);
        if(adminExistByAdminId(adminId)){
            Admin admin = getAdminByAdminId(adminId);
            return passwordEncoder.matches(adminPassword,admin.getAdminPassword());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Admin not Found");
    }

    public void updateAdminPassword(String adminId, String newAdminPassword) {
        Admin admin = getAdminByAdminId(adminId);
        admin.setAdminPassword(newAdminPassword);
        saveAdmin(admin);
    }

    public boolean existsByEmailId(String emailId) {
        return adminRepository.existsByEmailId(emailId);
    }

    public boolean existsByAdminId(String adminId) {
        return adminRepository.existsByAdminId(adminId);
    }

    public Optional<Admin> findByAdminId(String adminId) {
        return adminRepository.findByAdminId(adminId);
    }

    public Admin findByEmailId(String adminId) {
        return adminRepository.findByEmailId(adminId);
    }
}
