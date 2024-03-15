package com.example.facilitymanagement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.facilitymanagement.model.ReservationModel;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {
    @Query("SELECT r FROM ReservationModel r WHERE r.facility.id = :facilityId AND ((r.startDateTime <= :startDateTime AND r.endDateTime >= :startDateTime) OR (r.startDateTime < :endDateTime AND r.endDateTime >= :endDateTime) OR (r.startDateTime >= :startDateTime AND r.endDateTime <= :endDateTime))")
    List<ReservationModel> findOverlappingReservations(Long facilityId, LocalDateTime startDateTime,
            LocalDateTime endDateTime);
}
