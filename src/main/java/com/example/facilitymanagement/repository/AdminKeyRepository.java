package com.example.facilitymanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.facilitymanagement.model.AdminKeyModel;

@Repository
public interface AdminKeyRepository extends JpaRepository<AdminKeyModel, Long> {
    Optional<AdminKeyModel> findByAdminKeyValue(String adminKeyValue);
}
