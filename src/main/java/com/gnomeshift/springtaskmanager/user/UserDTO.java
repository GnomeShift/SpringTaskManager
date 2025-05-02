package com.gnomeshift.springtaskmanager.user;

import com.gnomeshift.springtaskmanager.user.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Email
    @NotNull
    @Size(max = 100)
    private String email;

    private Set<Role> roles;
}
