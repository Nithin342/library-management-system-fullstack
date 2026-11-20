package com.library.managament.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.managament.dto.UserDTO;
import com.library.managament.entity.User;
import com.library.managament.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserDTO userDTO) {

        User user = new User();

        user.setUsername(userDTO.getUsername());

        user.setPassword(
                passwordEncoder.encode(userDTO.getPassword())
        );

        user.setRole(userDTO.getRole());

        User savedUser = userRepository.save(user);

        UserDTO response = new UserDTO();

        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setRole(savedUser.getRole());

        return response;
    }

}
