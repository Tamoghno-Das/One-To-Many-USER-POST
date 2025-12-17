package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotEmpty(message = "Title can't be empty")
    @Column(name = "title", nullable = false, length = 100)
    String title;
    @NotBlank(message = "description can't be blank")
    @Column(name = "description,", nullable = false, length = 200 )
    String description;
    @NotNull()
    @Column(name = "content",length = 200)
    String content;

}
