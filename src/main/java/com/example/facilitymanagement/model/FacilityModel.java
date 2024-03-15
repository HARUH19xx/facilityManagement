package com.example.facilitymanagement.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FacilityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String facilityname;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private String description;

    // ゲッターとセッター
    public Long getId() {
        return id;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    // 設定するだけなので、戻り値をvoidにする
    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}