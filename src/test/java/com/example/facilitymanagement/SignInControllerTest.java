package com.example.facilitymanagement;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.example.facilitymanagement.config.WebSecurityConfig;
import com.example.facilitymanagement.controller.SignInController;
import com.example.facilitymanagement.model.SignInModel;
import com.example.facilitymanagement.model.UserModel;
import com.example.facilitymanagement.repository.UserRepository;
import com.example.facilitymanagement.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.facilitymanagement.service.TokenBlackListService;

@WebMvcTest(controllers = SignInController.class)
@Import(WebSecurityConfig.class)
public class SignInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private TokenBlackListService tokenBlackListService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void signIn_ShouldReturnJwt_WhenCredentialsAreValid() throws Exception {
        SignInModel model = new SignInModel("user", "password");
        UserModel userModel = new UserModel();
        userModel.setUsername(model.getUsername());
        userModel.setPassword("encodedPassword");

        // ユーザーが存在し、パスワードが一致することを示すモックの設定
        given(userRepository.findByUsername(model.getUsername())).willReturn(Optional.of(userModel));
        given(passwordEncoder.matches("password", userModel.getPassword())).willReturn(true);
        given(jwtUtil.generateToken(model.getUsername())).willReturn("dummyJwtToken");

        mockMvc.perform(post("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(model)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", not(emptyOrNullString())));
    }

    @Test
    public void signIn_ShouldReturnUnauthorized_WhenCredentialsAreInvalid() throws Exception {
        SignInModel model = new SignInModel("user", "wrongPassword");

        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        String content = objectMapper.writeValueAsString(model);
        if (content != null) {
            mockMvc.perform(post("/api/signin")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(content))
                    .andExpect(status().isUnauthorized());
        }
    }
}
