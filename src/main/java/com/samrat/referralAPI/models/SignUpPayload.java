package com.samrat.referralAPI.models;

public class SignUpPayload {
    private String email;
    private String password;
    private String refGain;
    private Role role; // USER or ADMIN

    public SignUpPayload(String email, String password, String refGain, Role role) {
        this.email = email;
        this.password = password;
        this.refGain = refGain;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRefGain() {
        return refGain;
    }

    public void setRefGain(String refGain) {
        this.refGain = refGain;
    }
}
