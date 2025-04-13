package com.subhajit.microservices.user_service.service;

import com.subhajit.microservices.user_service.dto.UserEventDTO;
import com.subhajit.microservices.user_service.dto.UserRequestDTO;
import com.subhajit.microservices.user_service.dto.UserResponseDTO;
import com.subhajit.microservices.user_service.dto.UserUpdateRequestDTO;
import com.subhajit.microservices.user_service.model.Event;
import com.subhajit.microservices.user_service.model.User;
import com.subhajit.microservices.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserEventProducer producer;

    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword()) // hash it while adding jwt auth
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(dto.getRole())
                .build();

        user = userRepository.save(user);

        // creating the event to be sent to journal service
        UserEventDTO event = UserEventDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .eventType(Event.CREATE)
                .payload("Created a User with id: " + user.getId() + ", username: " + user.getUsername())
                .build();

        producer.sendUserEvent(event);
        return toDTO(user);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(Objects.nonNull(dto.getEmail())){
            user.setEmail(dto.getEmail());
        }
        if(Objects.nonNull(dto.getFirstName())){
            user.setFirstName(dto.getFirstName());
        }
        if(Objects.nonNull(dto.getLastName())){
            user.setLastName(dto.getLastName());
        }

        user = userRepository.save(user);

        // creating the event to be sent to journal service
        UserEventDTO event = UserEventDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .eventType(Event.UPDATE)
                .payload("Updated a User with id: " + user.getId() + ", username: " + user.getUsername())
                .build();

        producer.sendUserEvent(event);
        return toDTO(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);

        // creating the event to be sent to journal service
        UserEventDTO event = UserEventDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .eventType(Event.DELETE)
                .payload("Deleted a User with id: " + user.getId() + ", username: " + user.getUsername())
                .build();

        producer.sendUserEvent(event);
    }

    private UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}
