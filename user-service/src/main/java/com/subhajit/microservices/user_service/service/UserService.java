package com.subhajit.microservices.user_service.service;

import com.subhajit.microservices.user_service.dto.*;
import com.subhajit.microservices.user_service.model.Event;
import com.subhajit.microservices.user_service.model.User;
import com.subhajit.microservices.user_service.repository.UserRepository;
import com.subhajit.microservices.user_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventProducer producer;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserResponseDTO registerUser(UserRequestDTO dto) {
        // check if user already exists
        if(userRepository.existsByUsername(dto.getUsername())){
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword())) // hash it
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

    public String loginUser(AuthenticationCredentialsRequestDTO authCredentialsRequestDTO) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authCredentialsRequestDTO.getUsername(), authCredentialsRequestDTO.getPassword()));

            // this will call the loadUserByUsername method from UserServiceDetailsImpl class, and get the userDetails from the db with help of username
            User user = (User) authenticate.getPrincipal();
            user.setPassword(null);
            return jwtUtil.generateToken(user);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials");
        }
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
