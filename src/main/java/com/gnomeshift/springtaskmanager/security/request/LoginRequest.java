package com.gnomeshift.springtaskmanager.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?~`\"'|\\\\/-]{8,}$")
    private String password;
}
