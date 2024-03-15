package com.example.facilitymanagement.dto;

import java.time.LocalDateTime;

public class ReservationResponseDto {
    private Long id;
    private String facilityName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    // コンストラクタ、ゲッター、セッター
    public ReservationResponseDto(Long id, String facilityName, LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        this.id = id;
        this.facilityName = facilityName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
