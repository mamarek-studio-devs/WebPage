package pl.mamarek.backend.blogpost.model;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BlogPostMapper {

    BlogPostDto toDto(BlogPost blogPost);

    BlogPost toEntity(BlogPostDto blogPostDto);

    void updateEntityFromDto(BlogPostDto dto, @MappingTarget BlogPost entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateEntityFromDto(BlogPostDto dto, @MappingTarget BlogPost entity);
}
