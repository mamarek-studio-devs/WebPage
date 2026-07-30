package pl.szyszlak.mamarek123.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Blog post data transfer object")
public class BlogPostDTO {
    @Schema(description = "Unique identifier of the blog post", example = "1")
    private Long id;

    @Schema(description = "Title of the blog post", example = "Hello world")
    private String title;

    @Schema(description = "Short summary of the blog post", example = "A short intro to the project")
    private String summary;
}