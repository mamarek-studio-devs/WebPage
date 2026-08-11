package pl.mamarek.backend.store.model;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record OrderDto(
        Long id,
        String customerName,
        String customerEmail,
        String status,
        Long totalAmount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
