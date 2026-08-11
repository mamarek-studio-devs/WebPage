package pl.mamarek.backend.blogpost.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BlogPostDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
