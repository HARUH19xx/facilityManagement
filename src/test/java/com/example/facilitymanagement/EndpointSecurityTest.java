// package com.example.facilitymanagement;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import com.example.facilitymanagement.model.EquipmentModel;
// import com.example.facilitymanagement.repository.EquipmentRepository;
// import com.example.facilitymanagement.repository.ReservationRepository;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class EndpointSecurityTest {

// @Autowired
// private MockMvc mockMvc;

// @Autowired
// private EquipmentRepository equipmentRepository;

// @Autowired
// private ReservationRepository reservationRepository;

// @BeforeEach
// public void cleanUp() {
// reservationRepository.deleteAll();
// equipmentRepository.deleteAll();
// }

// @Test
// @WithMockUser(roles = "ADMIN")
// public void whenAccessedByAdmin_thenSucceeds() throws Exception {
// EquipmentModel equipment = new EquipmentModel();
// equipment.setEquipmentType("pen");
// equipment.setEquipmentQuantity(10);
// equipment.setEquipmentReplenishRate(5);
// equipment.setEquipmentMaxQuantity(20);

// mockMvc.perform(MockMvcRequestBuilders.post("/api/equipment")
// .contentType(MediaType.APPLICATION_JSON)
// .content(new ObjectMapper().writeValueAsString(equipment)))
// .andExpect(MockMvcResultMatchers.status().isCreated()); // ステータスコード201を期待
// }

// @Test
// @WithMockUser(roles = "USER")
// public void whenAccessedByNonAdmin_thenDenied() throws Exception {
// EquipmentModel equipment = new EquipmentModel();
// equipment.setEquipmentType("paper");
// equipment.setEquipmentQuantity(10);
// equipment.setEquipmentReplenishRate(5);
// equipment.setEquipmentMaxQuantity(20);

// mockMvc.perform(MockMvcRequestBuilders.post("/api/equipment")
// .contentType(MediaType.APPLICATION_JSON)
// .content(new ObjectMapper().writeValueAsString(equipment)))
// .andExpect(MockMvcResultMatchers.status().isForbidden()); // ステータスコード403を期待
// }
// }
