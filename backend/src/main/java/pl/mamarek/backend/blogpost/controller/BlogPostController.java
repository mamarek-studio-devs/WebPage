package pl.mamarek.backend.blogpost.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mamarek.backend.blogpost.model.BlogPostDto;
import pl.mamarek.backend.blogpost.service.BlogPostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @GetMapping
    public List<BlogPostDto> getAllPosts() {
        return blogPostService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDto> getPostById(@PathVariable Long id) {
        return blogPostService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BlogPostDto> createPost(@RequestBody BlogPostDto post) {
        BlogPostDto savedPost = blogPostService.createPost(post);
        return ResponseEntity.status(201).body(savedPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostDto> updatePost(@PathVariable Long id, @RequestBody BlogPostDto postDetails) {
        return blogPostService.fullyUpdatePost(id, postDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BlogPostDto> partialUpdatePost(@PathVariable Long id, @RequestBody BlogPostDto postDetails) {
        return blogPostService.partiallyUpdatePost(id, postDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> headPost(@PathVariable Long id) {
        return blogPostService.getPostById(id)
                .map(_ -> ResponseEntity.ok().<Void>build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (blogPostService.deletePost(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
