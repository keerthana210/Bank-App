package com.keerthana.bank_app.repository;

import com.keerthana.bank_app.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByEmailId(String emailId);

    boolean existsByAdminId(String adminId);

    Admin getAdminByAdminId(String adminId);

    Optional<Admin> findByAdminId(String adminId);

    Admin findByEmailId(String adminId);
}
