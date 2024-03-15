package com.example.facilitymanagement;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.facilitymanagement.controller.ReservationController;
import com.example.facilitymanagement.repository.FacilityRepository;
import com.example.facilitymanagement.repository.UserRepository;
import com.example.facilitymanagement.service.ReservationService;

@WebMvcTest(controllers = ReservationController.class)
public class ReservationCancelTest {

    // テストされているクラスでインポートされているクラスは、基本的にモックしておく
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FacilityRepository facilityRepository;

    @Autowired
    private org.springframework.web.context.WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void cancelReservation_Success() throws Exception {
        // キャンセル成功を模擬するための設定
        Long reservationId = 1L;
        doNothing().when(reservationService).cancelReservation(anyLong());

        mockMvc.perform(put("/api/reservations/cancel/" + reservationId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("予約がキャンセルされました")));
    }

    @Test
    public void cancelReservation_Failure_NotFound() throws Exception {
        // 存在しない予約のキャンセルを試みた際にRuntimeExceptionを投げるように設定
        doThrow(new RuntimeException("予約が見つかりません")).when(reservationService).cancelReservation(anyLong());

        mockMvc.perform(put("/api/reservations/cancel/" + 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("予約が見つかりません")));
    }
}
