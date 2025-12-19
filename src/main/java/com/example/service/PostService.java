package com.example.service;

import com.example.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto addNewPost(Long userId, PostDto postDto);
    PostDto getPostById(Long userId, Long postId);
    List<PostDto> getPostsByUserId(Long userId);
    PostDto updatePost(Long userId,Long postId,PostDto postDto);
    void deletePost(Long userId,Long postId);

}
