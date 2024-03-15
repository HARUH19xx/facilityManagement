package com.example.facilitymanagement;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.facilitymanagement.controller.SignUpController;
import com.example.facilitymanagement.model.RoleModel;
import com.example.facilitymanagement.model.UserModel;
import com.example.facilitymanagement.repository.RoleRepository;
import com.example.facilitymanagement.repository.UserRepository;

@WebMvcTest(SignUpController.class)
@TestPropertySource(properties = { "ADMIN_SECRET_KEY=admin_secret_key" })
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // 名前が重複しているとき、ユーザーを登録するかどうか
    @Test
    public void givenDuplicateUsername_whenSignUp_thenDoNotRegister() throws Exception {
        // リクエストボディを設定
        String jsonBody = "{\"username\":\"testUser\",\"password\":\"testPass\",\"dateOfBirth\":\"2000-01-01\"}";

        // ユーザー名が既に存在することを模擬
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new UserModel()));

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("このユーザー名は既に使用されています")));
    }

    // ユーザー名が4文字未満の場合、ユーザーを登録するかどうか
    @Test
    public void givenUsernameLessThan4Chars_whenSignUp_thenDoNotRegister() throws Exception {
        String jsonBody = "{\"username\":\"abc\",\"password\":\"testPass\",\"dateOfBirth\":\"2000-01-01\"}";

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("ユーザー名は4文字以上20文字以下にしてください")));
    }

    // ユーザー名が20文字を超える場合、ユーザーを登録するかどうか
    @Test
    public void givenUsernameMoreThan20Chars_whenSignUp_thenDoNotRegister() throws Exception {
        String jsonBody = "{\"username\":\"abcdefghijklmnopqrstu\",\"password\":\"testPass\",\"dateOfBirth\":\"2000-01-01\"}";

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("ユーザー名は4文字以上20文字以下にしてください")));
    }

    // パスワードが8文字未満の場合、ユーザーを登録するかどうか
    @Test
    public void givenPasswordLessThan8Chars_whenSignUp_thenDoNotRegister() throws Exception {
        String jsonBody = "{\"username\":\"testUser\",\"password\":\"test7\",\"dateOfBirth\":\"2000-01-01\"}";

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("パスワードは8文字以上40文字以下にしてください")));
    }

    // パスワードが40文字を超える場合、ユーザーを登録するかどうか
    @Test
    public void givenPasswordMoreThan40Chars_whenSignUp_thenDoNotRegister() throws Exception {
        String jsonBody = "{\"username\":\"testUser\",\"password\":\"" + "p".repeat(41)
                + "\",\"dateOfBirth\":\"2000-01-01\"}";

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("パスワードは8文字以上40文字以下にしてください")));
    }

    // 管理者としてサインアップするための秘密鍵が一致する場合に管理者を登録するかどうか
    @Test
    public void givenValidAdminSecretKey_whenSignUp_thenRegisterAsAdmin() throws Exception {
        // 管理者ロールの模擬設定
        RoleModel adminRole = new RoleModel();
        adminRole.setRoleName("ROLE_ADMIN");
        when(roleRepository.findByRoleName("ROLE_ADMIN")).thenReturn(adminRole);

        // 正しい管理者の秘密鍵を持つリクエストボディを設定
        String validAdminSecretKey = "admin_secret_key";
        String jsonBody = String.format(
                "{\"username\":\"adminUser\",\"password\":\"adminPass\",\"dateOfBirth\":\"2000-01-01\",\"secretKey\":\"%s\"}",
                validAdminSecretKey);

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("管理者登録に成功しました！")));

        // UserRepositoryのsaveメソッドが呼ばれたことを検証
        verify(userRepository, times(1)).save(any(UserModel.class));

        System.clearProperty("ADMIN_SECRET_KEY");
    }

    // 秘密鍵が一致しない場合には、管理者として登録しない
    @Test
    public void givenInvalidAdminSecretKey_whenSignUp_thenDoNotRegisterAsAdmin() throws Exception {
        String jsonBody = "{\"username\":\"testUser\",\"password\":\"testPass\",\"dateOfBirth\":\"2000-01-01\",\"secretKey\":\"invalidSecretKey\"}";

        System.setProperty("ADMIN_SECRET_KEY", "validSecretKey");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("管理者登録のための秘密鍵が間違っています")));

        System.clearProperty("ADMIN_SECRET_KEY");
    }

    // 秘密鍵が設定されていない場合にユーザーを登録するかどうか
    @Test
    public void givenNoSecretKey_whenSignUp_thenRegisterAsUser() throws Exception {
        String jsonBody = "{\"username\":\"testUser\",\"password\":\"testPass\",\"dateOfBirth\":\"2000-01-01\"}";

        System.setProperty("ADMIN_SECRET_KEY", "validSecretKey");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ユーザー登録に成功しました！")));

        System.clearProperty("ADMIN_SECRET_KEY");
    }

}
