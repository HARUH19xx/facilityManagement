package com.example.facilitymanagement.controller;

import java.util.Map;
import java.util.Collections;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class GreetingController {

    @GetMapping("/api/greeting")
    public Map<String, String> greeting() {
        // return Map.of("greeting", "Hello, World!");
        return Collections.singletonMap("greeting", "こんにちは、皆さん");
    }

    // ユーザー名を返すエンドポイント
    @GetMapping("/api/username")
    public Map<String, String> username() {
        return Collections.singletonMap("username", "user");
    }
}
