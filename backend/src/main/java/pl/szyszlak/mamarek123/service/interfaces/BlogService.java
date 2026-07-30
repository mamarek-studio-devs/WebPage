package pl.szyszlak.mamarek123.service.interfaces;

import pl.szyszlak.mamarek123.model.BlogPostDTO;

import java.util.List;

public interface BlogService {
    List<BlogPostDTO> getAllPosts();
    void savePost(BlogPostDTO postDTO);
    Long countPosts();
}