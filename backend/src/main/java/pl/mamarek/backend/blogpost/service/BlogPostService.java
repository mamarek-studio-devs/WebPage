package pl.mamarek.backend.blogpost.service;

import lombok.RequiredArgsConstructor;
import pl.mamarek.backend.blogpost.model.BlogPost;
import pl.mamarek.backend.blogpost.model.BlogPostDto;
import pl.mamarek.backend.blogpost.model.BlogPostMapper;
import pl.mamarek.backend.blogpost.repository.BlogPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final BlogPostMapper blogPostMapper;

    @Transactional(readOnly = true)
    public List<BlogPostDto> getAllPosts() {
        return blogPostRepository.findAll()
                .stream()
                .map(blogPostMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<BlogPostDto> getPostById(Long id) {
        return blogPostRepository.findById(id).map(blogPostMapper::toDto);
    }

    @Transactional
    public BlogPostDto createPost(BlogPostDto blogPostDto) {
        BlogPost blogPost = blogPostMapper.toEntity(blogPostDto);
        BlogPost savedPost = blogPostRepository.save(blogPost);
        return blogPostMapper.toDto(savedPost);
    }

    @Transactional
    public Optional<BlogPostDto> fullyUpdatePost(Long id, BlogPostDto blogPostDto) {
        return blogPostRepository.findById(id)
                .map(existingPost -> {
                    blogPostMapper.updateEntityFromDto(blogPostDto, existingPost);
                    BlogPost savedPost = blogPostRepository.save(existingPost);
                    return blogPostMapper.toDto(savedPost);
                });
    }

    @Transactional
    public Optional<BlogPostDto> partiallyUpdatePost(Long id, BlogPostDto blogPostDto) {
        return blogPostRepository.findById(id)
                .map(existingPost -> {
                    blogPostMapper.partialUpdateEntityFromDto(blogPostDto, existingPost);
                    BlogPost savedPost = blogPostRepository.save(existingPost);
                    return blogPostMapper.toDto(savedPost);
                });
    }

    @Transactional
    public boolean deletePost(Long id) {
        if (!blogPostRepository.existsById(id)) {
            return false;
        }

        blogPostRepository.deleteById(id);
        return true;
    }
}
