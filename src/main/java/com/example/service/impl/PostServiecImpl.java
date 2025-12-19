package com.example.service.impl;

import com.example.config.ModelMapperConfig;
import com.example.dto.PostDto;
import com.example.entity.Post;
import com.example.entity.User;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.PostRepository;
import com.example.repository.UserRepository;
import com.example.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiecImpl implements PostService {

    UserRepository userRepository;
    PostRepository postRepository;
    ModelMapper modelMapper;
    @Override
    public PostDto addNewPost(Long userId, PostDto postDto)
    {

        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found with id "+userId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);
        Post savedpost = postRepository.save(post);
        PostDto savedpostDto = modelMapper.map(savedpost, PostDto.class);
        savedpostDto.setId(savedpost.getUser().getId());
        return savedpostDto;
    }

    @Override
    public PostDto getPostById(Long userId, Long postId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found with id "+userId));

        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post Not Found with id "+postId));

        if(!post.getUser().getId().equals(user.getId()))
        {
            throw new ResourceNotFoundException("Post Does Not Belong to: "+userId);

        }
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setPostUserId(post.getUser().getId());
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByUserId(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found with id : "+userId));

        Optional<Post> posts = postRepository.findById(userId);

        return posts.stream().map((element) -> modelMapper.map(element, PostDto.class)).toList();


    }

}
