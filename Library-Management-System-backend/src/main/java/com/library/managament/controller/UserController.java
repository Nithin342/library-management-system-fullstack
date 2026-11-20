package com.library.managament.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.library.managament.dto.UserDTO;
import com.library.managament.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	 private final UserService userService;

	    public UserController(UserService userService) {
	        this.userService = userService;
	    }

	    @PostMapping
	    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
	        return userService.createUser(userDTO);
	    }
}
