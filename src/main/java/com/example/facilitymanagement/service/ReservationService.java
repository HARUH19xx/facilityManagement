package com.example.facilitymanagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.facilitymanagement.model.ReservationModel;
import com.example.facilitymanagement.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean isReservationAvailable(Long facilityId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<ReservationModel> overlappingReservations = reservationRepository.findOverlappingReservations(facilityId,
                startDateTime, endDateTime);
        return overlappingReservations.isEmpty();
    }

    public void saveReservation(ReservationModel reservation) {
        reservationRepository.save(reservation);
    }

    public void cancelReservation(Long reservationId) {
        if (reservationRepository.existsById(reservationId)) {
            reservationRepository.deleteById(reservationId);
        } else {
            throw new RuntimeException("予約が見つかりません");
        }
    }

    // 予約を取得
    public List<ReservationModel> findAllReservations() {
        return reservationRepository.findAll();
    }
}
