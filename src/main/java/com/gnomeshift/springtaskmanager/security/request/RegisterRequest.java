package com.gnomeshift.springtaskmanager.security.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Email
    @NotNull
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?~`\"'|\\\\/-]{8,}$")
    @NotNull
    private String password;

    private Set<String> role;
}
