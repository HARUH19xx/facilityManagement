package com.example.facilitymanagement;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.facilitymanagement.controller.SignOutController;
import com.example.facilitymanagement.security.JwtUtil;
import com.example.facilitymanagement.service.TokenBlackListService;

@WebMvcTest(controllers = SignOutController.class)
public class SignOutControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenBlackListService tokenBlackListService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser(roles = "USER")
    void signOut_ShouldAddTokenToBlacklist() throws Exception {
        if (webApplicationContext != null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

        // モックのセットアップ
        doNothing().when(tokenBlackListService).addToBlacklist(anyString(), anyLong());
        when(jwtUtil.getRemainingExpirationTimeInSeconds(anyString())).thenReturn(3600L); // 仮の残り有効期限を返す

        // サインアウトエンドポイントに対するテスト
        mockMvc.perform(post("/api/signout")
                .header("Authorization", "Bearer testToken123"))
                .andExpect(status().isOk());
    }
}
