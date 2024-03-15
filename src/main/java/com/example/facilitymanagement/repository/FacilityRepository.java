package com.example.facilitymanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.facilitymanagement.model.FacilityModel;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityModel, Long> {
    Optional<FacilityModel> findByFacilityname(String facilityname);
}