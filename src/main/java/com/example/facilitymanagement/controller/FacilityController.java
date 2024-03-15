package com.example.facilitymanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.facilitymanagement.model.FacilityModel;
import com.example.facilitymanagement.repository.FacilityRepository;

@RestController
public class FacilityController {

    // コンストラクタインジェクションを使用して、FacilityRepositoryをインスタンス化（循環参照対策）
    private final FacilityRepository facilityRepository;

    public FacilityController(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @PostMapping("/api/facility")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> facility(@Validated @RequestBody FacilityModel facilityModel) {
        // リクエストボディが空の場合は、400 Bad Requestを返す（実際にはあり得ない）
        if (facilityModel == null) {
            return ResponseEntity.badRequest().build();
        }

        // 施設名が空の場合は、400 Bad Requestを返す
        if (facilityModel.getFacilityname() == null || facilityModel.getFacilityname().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 施設名が重複している場合は、400 Bad Requestを返す
        if (facilityRepository.findByFacilityname(facilityModel.getFacilityname()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // IDかタイムスタンプが空の場合は、400 Bad Requestを返す
        if (facilityModel.getId() != null || facilityModel.getCreatedAt() != null) {
            return ResponseEntity.badRequest().build();
        }

        FacilityModel savedFacility = facilityRepository.save(facilityModel);
        return ResponseEntity.ok().body(savedFacility);
    }

    @GetMapping("/api/facility/show")
    public ResponseEntity<?> showFacility() {
        return ResponseEntity.ok().body(facilityRepository.findAll());
    }
}
