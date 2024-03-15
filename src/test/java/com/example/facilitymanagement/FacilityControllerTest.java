package com.example.facilitymanagement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.facilitymanagement.config.WebSecurityConfig;
import com.example.facilitymanagement.controller.FacilityController;
import com.example.facilitymanagement.model.FacilityModel;
import com.example.facilitymanagement.repository.FacilityRepository;
import com.example.facilitymanagement.security.JwtUtil;
import com.example.facilitymanagement.service.TokenBlackListService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = FacilityController.class)
@Import(WebSecurityConfig.class)
public class FacilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacilityRepository facilityRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenBlackListService tokenBlackListService;

    @WithMockUser(value = "admin")
    @Test
    public void facility_ShouldReturnOk_WhenFacilityDataIsValid() throws Exception {
        FacilityModel newFacility = new FacilityModel();
        newFacility.setFacilityname("newFacility");
        newFacility.setDescription("newDescription");

        given(facilityRepository.save(any(FacilityModel.class))).willReturn(newFacility);

        mockMvc.perform(post("/api/facility")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newFacility)))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "admin")
    @Test
    public void facility_ShouldReturnBadRequest_WhenFacilityDataIsNull() throws Exception {
        mockMvc.perform(post("/api/facility")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}")) // 空のJSONオブジェクトを送信
                .andExpect(status().isBadRequest());
    }
}
