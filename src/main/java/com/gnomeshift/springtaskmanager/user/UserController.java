package com.gnomeshift.springtaskmanager.user;

import com.gnomeshift.springtaskmanager.task.TaskDTO;
import com.gnomeshift.springtaskmanager.task.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/owned/{id}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByOwnerId(@PathVariable UUID id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskService.getAllTasksByOwnerId(id));
    }

    @GetMapping("/assigned/{id}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByAssigneeId(@PathVariable UUID id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskService.getAllTasksByAssigneeId(id));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @Valid @RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
