package com.example.service.impl;

import com.example.dto.PostDto;
import com.example.entity.Post;
import com.example.entity.User;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UserBadRequestException;
import com.example.repository.PostRepository;
import com.example.repository.UserRepository;
import com.example.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Override
    public PostDto addNewPost(Long userId, PostDto postDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        PostDto savedPostDto = modelMapper.map(savedPost, PostDto.class);
        savedPostDto.setUserId(user.getId());

        return savedPostDto;
    }


    @Override
    public PostDto getPostById(Long userId, Long postId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found with id: " + postId));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UserBadRequestException(
                    "Post with id " + postId + " does not belong to user " + userId);
        }

        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setUserId(userId);

        return postDto;
    }

    @Override
    public List<PostDto> getPostsByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        return user.getPost()
                .stream()
                .map(post -> {
                    PostDto dto = modelMapper.map(post, PostDto.class);
                    dto.setUserId(userId);
                    return dto;
                })
                .toList();
    }
    @Override
    public PostDto updatePost(Long userId, Long postId, PostDto postDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found with id: " + postId));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UserBadRequestException(
                    "Post with id " + postId + " does not belong to user " + userId);
        }

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        PostDto updatedPostDto = modelMapper.map(updatedPost, PostDto.class);
        updatedPostDto.setUserId(userId);

        return updatedPostDto;
    }

    @Override
    public void deletePost(Long userId, Long postId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found with id: " + postId));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UserBadRequestException(
                    "Post with id " + postId + " does not belong to user " + userId);
        }

        postRepository.delete(post);
    }
}
