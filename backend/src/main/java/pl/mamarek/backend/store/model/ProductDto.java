package pl.mamarek.backend.store.model;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        String sku,
        List<String> pictures,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
