package com.example.facilitymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.facilitymanagement.security.JwtUtil;
import com.example.facilitymanagement.service.TokenBlackListService;

@RestController
public class SignOutController {

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/api/signout")
    public ResponseEntity<?> signOut(@RequestHeader("Authorization") String authToken) {
        String token = authToken.substring(7); // "Bearer "を削除
        long remainingExpirationTimeInSeconds = jwtUtil.getRemainingExpirationTimeInSeconds(token);
        tokenBlackListService.addToBlacklist(token, remainingExpirationTimeInSeconds);
        return ResponseEntity.ok().build();
    }
}
