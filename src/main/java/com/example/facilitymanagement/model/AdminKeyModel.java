package com.example.facilitymanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AdminKeyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false, unique = true)
    private String adminKeyValue;

    public AdminKeyModel() {
    }

    public Long getAdminId() {
        return adminId;
    }

    public String getAdminKeyValue() {
        return adminKeyValue;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setAdminKeyValue(String adminKeyValue) {
        this.adminKeyValue = adminKeyValue;
    }
}
