package com.example.service.impl;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.exception.UserBadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

   UserRepository userRepository;
   ModelMapper modelMapper;

    @Override
    public UserDto addUser(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(user);

        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

        return savedUserDto;
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found with ID: "+id));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {

        List<User> users = userRepository.findAll();

        return users.stream().map(
                        (user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        if(id == null || id<=0)
        {
            throw new ResourceNotFoundException("User not found with id: "+id);

        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserBadRequestException("DATA INSUFFICIENT!!!"));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(user); // update
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public String deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.deleteById(id);

        return "Succesfully Deleted id"+id;
    }
}