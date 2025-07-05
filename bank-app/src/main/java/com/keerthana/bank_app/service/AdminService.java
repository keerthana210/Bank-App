package com.keerthana.bank_app.service;

import com.keerthana.bank_app.model.Admin;
import com.keerthana.bank_app.model.User;
import com.keerthana.bank_app.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin addAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    public Admin getAdminById(int id){
        return adminRepository.getReferenceById(id);
    }
    public boolean adminExistById(int id) {
        return adminRepository.existsById(id);
    }

    public boolean validateAdminCredentials(int id, String adminPassword) {
        System.out.println(id);
        return (getAdminById(id).getAdminPassword()).equals(adminPassword);
    }
}
