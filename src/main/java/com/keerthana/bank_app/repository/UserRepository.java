package com.keerthana.bank_app.repository;

import com.keerthana.bank_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccNumber(String accNumber);
    Optional<User> findByAccNumber(String accNumber);

    User findUserByAccNumber(String accNumber);

    Optional<User> findByAccNumberOrUserId(String loginId, String loginId1);

    boolean existsByUserId(String loginId);

    User findUserByUserId(String loginId);
}
