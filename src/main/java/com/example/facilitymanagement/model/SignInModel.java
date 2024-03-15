package com.example.facilitymanagement.model;

public class SignInModel {
    private String username;
    private String password;

    public SignInModel() {
    }

    public SignInModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ゲッターとセッター
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
