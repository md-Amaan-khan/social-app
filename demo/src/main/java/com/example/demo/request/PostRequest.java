package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private Long authorId;
    @NotBlank
    private String createdAt;
    @NotBlank
    private String updatedAt;
}
