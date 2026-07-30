package pl.szyszlak.mamarek123.model;

import org.springframework.stereotype.Component;
import pl.szyszlak.mamarek123.persistence.entity.BlogPost;

@Component
public class BlogPostMapper {

    public BlogPostDTO toDto(BlogPost entity) {
        return BlogPostDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .summary(entity.getSummary())
                .build();
    }

    public BlogPost toEntity(BlogPostDTO dto) {
        return BlogPost.builder()
                .title(dto.getTitle())
                .summary(dto.getSummary())
                .build();
    }
}