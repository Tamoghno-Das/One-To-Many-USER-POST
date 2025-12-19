package com.example.dto;

import com.example.entity.Post;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    List<PostDto> posts;
}
