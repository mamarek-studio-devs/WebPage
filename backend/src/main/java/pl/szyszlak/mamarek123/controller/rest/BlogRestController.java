package pl.szyszlak.mamarek123.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szyszlak.mamarek123.model.BlogPostDTO;
import pl.szyszlak.mamarek123.service.interfaces.BlogService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Blog post management endpoints")
public class BlogRestController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    @Operation(summary = "Get all blog posts", description = "Returns every blog post currently stored in the system", responses = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
    })
    public List<BlogPostDTO> getAllPosts() {
        return blogService.getAllPosts();
    }
}