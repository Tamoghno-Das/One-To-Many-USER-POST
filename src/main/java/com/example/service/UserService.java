package com.example.service;

import com.example.dto.UserDto;

import java.util.List;

public interface UserService
{
        UserDto addUser(UserDto userDTO);
        UserDto getUser(Long id);
        List<UserDto> getAllUser();

        UserDto updateUser(UserDto userDTO, Long id);
        String deleteUser(Long id);
}
