package com.gnomeshift.springtaskmanager.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskDTO {
    private UUID id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String description;

    private TaskStatus status;

    private UUID ownerId;

    private UUID assigneeId;
}
