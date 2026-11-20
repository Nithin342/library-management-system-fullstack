package com.library.managament.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.managament.dto.LoginDTO;
import com.library.managament.entity.User;
import com.library.managament.exception.InvalidCredentialsException;
import com.library.managament.repository.UserRepository;
import com.library.managament.service.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginController(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping
    public String login(@Valid @RequestBody LoginDTO loginDTO) {

        User user = userRepository
                .findByUsername(loginDTO.getUsername())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid username or password"));

        if (!passwordEncoder.matches(
                loginDTO.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid username or password");
        }

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );
    }
}