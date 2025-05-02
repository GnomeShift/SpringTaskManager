    package com.gnomeshift.springtaskmanager.task;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import com.gnomeshift.springtaskmanager.user.User;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;
    import lombok.Data;

    import java.time.LocalDateTime;
    import java.util.UUID;

    @Entity
    @Table(name = "tasks")
    @Data
    @JsonIdentityInfo(scope = Task.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public class Task {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @NotNull
        @Size(max = 100)
        private String title;

        @NotNull
        private String description;

        @NotNull
        @Enumerated(EnumType.STRING)
        private TaskStatus status;

        @NotNull
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        @JsonBackReference(value = "ownerReference")
        private User owner;

        @ManyToOne
        @JoinColumn(name = "assignee_id")
        @JsonBackReference(value = "assigneeReference")
        private User assignee;

        @NotNull
        private LocalDateTime createdAt;

        @NotNull
        private LocalDateTime updatedAt;

        @PrePersist
        protected void onCreate() {
            createdAt = LocalDateTime.now();
            updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        protected void onUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }
