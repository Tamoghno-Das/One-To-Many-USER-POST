package com.example.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    Long id;
    String title;
    String description;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long userId;

}
