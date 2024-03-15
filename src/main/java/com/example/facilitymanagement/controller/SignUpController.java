package com.example.facilitymanagement.controller;

import java.util.Collections;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.facilitymanagement.dto.SignUpRequestDto;
import com.example.facilitymanagement.model.RoleModel;
import com.example.facilitymanagement.model.UserModel;
import com.example.facilitymanagement.repository.RoleRepository;
import com.example.facilitymanagement.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class SignUpController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;
    private final HttpServletResponse response;

    @Autowired
    public SignUpController(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, Environment env, HttpServletResponse response) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.response = response;
    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        try {

            // ユーザー名の重複チェック
            if (userRepository.findByUsername(signUpRequestDto.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("このユーザー名は既に使用されています");
            }

            // 入力検証（例：パスワードの複雑さ、ユーザー名とパスワードの長さなど）
            // Usernameの長さが4文字以上20文字以下であることを確認
            else if (signUpRequestDto.getUsername().length() < 4 ||
                    signUpRequestDto.getUsername().length() > 20) {
                return ResponseEntity.badRequest().body("ユーザー名は4文字以上20文字以下にしてください");
            }

            // Passwordの長さが8文字以上40文字以下であることを確認
            else if (signUpRequestDto.getPassword().length() < 8 ||
                    signUpRequestDto.getPassword().length() > 40) {
                return ResponseEntity.badRequest().body("パスワードは8文字以上40文字以下にしてください");
            }

            // パスワードのエンコード
            String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

            // ユーザーの作成
            UserModel user = new UserModel();
            user.setUsername(signUpRequestDto.getUsername());
            user.setPassword(encodedPassword);
            user.setDateOfBirth(signUpRequestDto.getDateOfBirth());

            // ロールの割り当て
            // 秘密鍵が一致する場合に管理者を登録
            String secretKey = env.getProperty("ADMIN_SECRET_KEY");
            if (signUpRequestDto.getSecretKey() != null) {
                if (signUpRequestDto.getSecretKey().equals(secretKey)) {
                    RoleModel adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
                    if (adminRole != null) {
                        user.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
                    }
                    userRepository.save(user);
                    return ResponseEntity.ok().body("管理者登録に成功しました！");
                } else {
                    // 秘密鍵が間違っている場合は警告を出して処理を停止
                    return ResponseEntity.badRequest().body("管理者登録のための秘密鍵が間違っています");
                }
            } else {
                // ロールが割り当てられていない場合、ユーザーのロールを割り当てる
                RoleModel userRole = roleRepository.findByRoleName("ROLE_USER");
                if (userRole != null) {
                    user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
                }
                userRepository.save(user);
                return ResponseEntity.ok().body("ユーザー登録に成功しました！");
            }
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("ユーザー登録に失敗しました！");

        }
    }
}
