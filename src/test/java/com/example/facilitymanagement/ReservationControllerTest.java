package com.example.facilitymanagement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.facilitymanagement.controller.ReservationController;
import com.example.facilitymanagement.dto.ReservationRequestDto;
import com.example.facilitymanagement.model.FacilityModel;
import com.example.facilitymanagement.model.UserModel;
import com.example.facilitymanagement.repository.FacilityRepository;
import com.example.facilitymanagement.repository.UserRepository;
import com.example.facilitymanagement.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FacilityRepository facilityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("testuser",
                        "password", AuthorityUtils.createAuthorityList("ROLE_USER")));
    }

    @Test
    public void createReservation_Success() throws Exception {
        // 成功時のテストケース
        when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(new UserModel()));
        when(facilityRepository.findByFacilityname(any())).thenReturn(java.util.Optional.of(new FacilityModel()));
        when(reservationService.isReservationAvailable(any(), any(), any())).thenReturn(true);

        ReservationRequestDto requestDto = new ReservationRequestDto();
        requestDto.setFacilityName("facility1");
        requestDto.setStartDateTime(LocalDateTime.now());
        requestDto.setEndDateTime(LocalDateTime.now().plusHours(2));

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createReservation_Failure_AlreadyBooked() throws Exception {
        // 予約不可能（既に予約済み）のテストケース
        UserModel user = new UserModel();
        FacilityModel facility = new FacilityModel();
        when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
        when(facilityRepository.findByFacilityname(any())).thenReturn(java.util.Optional.of(facility));
        when(reservationService.isReservationAvailable(any(), any(), any())).thenReturn(false);

        ReservationRequestDto requestDto = new ReservationRequestDto();
        requestDto.setFacilityName("facility1");
        requestDto.setStartDateTime(LocalDateTime.now());
        requestDto.setEndDateTime(LocalDateTime.now().plusHours(2));

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}
