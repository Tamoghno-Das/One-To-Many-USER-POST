package com.example.service.impl;

import com.example.dto.PostDto;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UserBadRequestException;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepository userRepository;
    ModelMapper modelMapper;

    @Override
    public UserDto addUser(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);

        return mapUserToDto(savedUser);
    }

    @Override
    public UserDto getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return mapUserToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {

        return userRepository.findAll()
                .stream()
                .map(this::mapUserToDto)
                .toList();
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserBadRequestException("User not found with id: " + id));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(user);
        return mapUserToDto(updatedUser);
    }

    @Override
    public String deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
        return "Successfully deleted user with id " + id;
    }

    private UserDto mapUserToDto(User user) {

        UserDto userDto = modelMapper.map(user, UserDto.class);

        userDto.setPosts(userDto.getPosts());

        if (user.getPost() != null && !user.getPost().isEmpty()) {

            List<PostDto> postDtos = user.getPost()
                    .stream()
                    .map(post -> {
                        PostDto dto = modelMapper.map(post, PostDto.class);
                        dto.setUserId(user.getId());
                        return dto;
                    })
                    .toList();

            userDto.setPosts(postDtos);
        }

        return userDto;
    }

    // USED TO LOAD THE USER BY EMAIL OR USERNAME (SPRING SECURITY)

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).
                orElseThrow(() -> new ResourceNotFoundException("User with email "+username+" not found"));
    }
}
