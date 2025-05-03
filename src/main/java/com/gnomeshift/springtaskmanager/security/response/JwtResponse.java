package com.gnomeshift.springtaskmanager.security.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UUID id;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, UUID id, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}
