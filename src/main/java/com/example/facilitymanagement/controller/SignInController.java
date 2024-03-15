package com.example.facilitymanagement.controller;

import com.example.facilitymanagement.model.SignInModel;
import com.example.facilitymanagement.model.UserModel;
import com.example.facilitymanagement.repository.UserRepository;
import com.example.facilitymanagement.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class SignInController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/api/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInModel signInModel) {
        Optional<UserModel> userOptional = userRepository.findByUsername(signInModel.getUsername());
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (passwordEncoder.matches(signInModel.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("token", token);
                return ResponseEntity.ok(tokenMap);
            }
        }
        return ResponseEntity.status(401).build();
    }
}
