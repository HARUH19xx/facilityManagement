package com.example.facilitymanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.facilitymanagement.dto.ErrorResponseDto;
import com.example.facilitymanagement.dto.ReservationRequestDto;
import com.example.facilitymanagement.model.FacilityModel;
import com.example.facilitymanagement.model.ReservationModel;
import com.example.facilitymanagement.model.UserModel;
import com.example.facilitymanagement.repository.FacilityRepository;
import com.example.facilitymanagement.repository.ReservationRepository;
import com.example.facilitymanagement.repository.UserRepository;
import com.example.facilitymanagement.service.ReservationService;
import com.example.facilitymanagement.dto.ReservationResponseDto;

@RestController
public class ReservationController {

    private final ReservationService reservationService;
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationService reservationService, UserRepository userRepository,
            FacilityRepository facilityRepository, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/api/reservations")
    public ResponseEntity<?> createReservation(@Validated @RequestBody ReservationRequestDto request) {
        try {
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String currentUsername = authentication.getName();

            // ログにユーザー名を出力
            System.out.println("ユーザー名: " + currentUsername);

            UserModel user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));

            String facilityName = request.getFacilityName();
            FacilityModel facility = facilityRepository.findByFacilityname(facilityName)
                    .orElseThrow(() -> new RuntimeException("施設が見つかりません"));

            if (!reservationService.isReservationAvailable(facility.getId(), request.getStartDateTime(),
                    request.getEndDateTime())) {
                return ResponseEntity.badRequest().body(new ErrorResponseDto("この施設は既に予約済みです"));
            }

            ReservationModel reservation = new ReservationModel();
            reservation.setUser(user);
            reservation.setFacility(facility);
            reservation.setStartDateTime(request.getStartDateTime());
            reservation.setEndDateTime(request.getEndDateTime());

            reservationService.saveReservation(reservation);

            // ログに予約情報を出力
            System.out.println("予約情報: " + reservation);

            // DTOを使用して、予約が完了したことを通知（このDTO作成を忘れないこと）
            return new ResponseEntity<>(new ErrorResponseDto("予約が完了しました"), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/api/reservations/show")
    public ResponseEntity<?> showReservations() {
        List<ReservationModel> reservations = reservationRepository.findAll();
        List<ReservationResponseDto> dtos = reservations.stream().map(reservation -> {
            FacilityModel facility = reservation.getFacility();
            // facility (および user) の遅延ロードされたプロパティを必要に応じて扱う
            String facilityName = facility != null ? facility.getFacilityname() : "施設名不明";
            return new ReservationResponseDto(
                    reservation.getId(),
                    facilityName,
                    reservation.getStartDateTime(),
                    reservation.getEndDateTime());
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtos);
    }

    @PutMapping("/api/reservations/cancel/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId) {
        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.ok().body("予約がキャンセルされました");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
