package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto savedUser = userService.addUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@Valid @PathVariable Long id)
    {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @DeleteMapping("{id}")
    public String deleteUserById(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return "User is Deleted with id: "+id;

    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Long id, @RequestBody UserDto userDto)
    {
        return ResponseEntity.ok(userService.updateUser(userDto,id));

    }
}
