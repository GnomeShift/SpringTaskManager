package com.gnomeshift.springtaskmanager.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gnomeshift.springtaskmanager.user.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import com.gnomeshift.springtaskmanager.task.Task;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@JsonIdentityInfo(scope = User.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Email
    @NotNull
    @Size(max = 100)
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?~`\"'|\\\\/-]{8,}$")
    @NotNull
    private String password;

    @NotNull
    @Column(columnDefinition = "integer default 0")
    private int failedLoginAttempt;

    private LocalDateTime lockTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "ownerReference")
    private List<Task> ownedTasks;

    @OneToMany(mappedBy = "assignee")
    @JsonManagedReference(value = "assigneeReference")
    private List<Task> assignedTasks;
}
