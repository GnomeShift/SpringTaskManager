package com.gnomeshift.springtaskmanager.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    public User updateUser(UUID id, User user) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        }
        else {
            return null;
        }
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
