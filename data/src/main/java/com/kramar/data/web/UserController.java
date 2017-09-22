package com.kramar.data.web;

import com.kramar.data.service.AuthenticationService;
import com.kramar.data.dto.ChangePasswordDto;
import com.kramar.data.dto.UserDto;
import com.kramar.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(UserController.REQUEST_MAPPING)
public class UserController {

    public static final String REQUEST_MAPPING = "/users";

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;


    @GetMapping(value = "/info")
    public UserDto getCurrentUser() {
        UUID userId = authenticationService.getCurrentUserId();
        return userService.getUserById(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userService.getAllUser(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Validated UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto modifyUser(@PathVariable("id") UUID id, @RequestBody @Validated UserDto userDto) {
        return userService.modifyUserById(id, userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") UUID id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/changepassword/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable("id") UUID id, @RequestBody @Validated ChangePasswordDto passwordDto) {
        passwordDto.setOldPassword(passwordEncoder.encode(passwordDto.getOldPassword()));
        passwordDto.setNewPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userService.changePassword(id, passwordDto);
    }
}
