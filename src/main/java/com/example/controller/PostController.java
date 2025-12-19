package com.example.controller;

import com.example.dto.PostDto;
import com.example.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PostController
{
    PostService postService;

    @PostMapping("/{userId}/createpost")
    public ResponseEntity<PostDto> createUser(@Valid @PathVariable Long userId, @RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.addNewPost(userId, postDto), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/posts/{postId}")
    public ResponseEntity<PostDto> getUserById( @Valid @PathVariable Long userId, @Valid @PathVariable Long postId)
    {
        return ResponseEntity.ok(postService.getPostById(userId, postId));
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId)
    {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @PutMapping("/{userId}/posts/{postId}")
    public ResponseEntity<PostDto> updatePost
            (@Valid @PathVariable Long userId,@PathVariable Long postId, @RequestBody PostDto postDto )
    {
        return ResponseEntity.ok(postService.updatePost(userId,postId,postDto));
    }

    @DeleteMapping("/{userId}/delete/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT )
    public void deletePost(@Valid @PathVariable Long userId,@PathVariable Long postId)
    {
        postService.deletePost(userId, postId);
    }

}
