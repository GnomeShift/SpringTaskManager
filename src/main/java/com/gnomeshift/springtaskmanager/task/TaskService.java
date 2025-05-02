package com.gnomeshift.springtaskmanager.task;

import com.gnomeshift.springtaskmanager.user.User;
import com.gnomeshift.springtaskmanager.user.UserDTO;
import com.gnomeshift.springtaskmanager.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private TaskDTO convertToDto(Task task) {
        TaskDTO dto = new TaskDTO();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());

        if (task.getOwner() != null) {
            UserDTO ownerDTO = new UserDTO();

            ownerDTO.setId(task.getOwner().getId());
            ownerDTO.setName(task.getOwner().getName());
            ownerDTO.setEmail(task.getOwner().getEmail());
            ownerDTO.setRoles(task.getOwner().getRoles());
            dto.setOwner(ownerDTO);
        }

        if (task.getAssignee() != null) {
            UserDTO assigneeDTO = new UserDTO();

            assigneeDTO.setId(task.getAssignee().getId());
            assigneeDTO.setName(task.getAssignee().getName());
            assigneeDTO.setEmail(task.getAssignee().getEmail());
            assigneeDTO.setRoles(task.getAssignee().getRoles());
            dto.setAssignee(assigneeDTO);
        }

        return dto;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public TaskDTO getTaskById(UUID id) {
        return taskRepository.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public List<TaskDTO> getAllTasksByOwnerId(UUID id) {
        return taskRepository.findAllByOwnerId(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<TaskDTO> getAllTasksByAssigneeId(UUID id) {
        return taskRepository.findAllByAssigneeId(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO) {
        User owner = userRepository.findById(taskDTO.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Task task = new Task();

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setOwner(owner);

        if (taskDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(taskDTO.getAssigneeId())
                    .orElseThrow(() -> new EntityNotFoundException("Assignee not found"));
            task.setAssignee(assignee);
        }

        return convertToDto(taskRepository.save(task));
    }

    @Transactional
    public TaskDTO updateTask(UUID id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        User owner = userRepository.findById(taskDTO.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setOwner(owner);

        if (taskDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(taskDTO.getAssigneeId())
                    .orElseThrow(() -> new EntityNotFoundException("Assignee not found"));
            task.setAssignee(assignee);
        }
        else {
            task.setAssignee(null);
        }

        return convertToDto(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found");
        }

        taskRepository.deleteById(id);
    }
}
