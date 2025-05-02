package com.gnomeshift.springtaskmanager.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByOwnerId(UUID id);
    List<Task> findAllByAssigneeId(UUID id);
}
