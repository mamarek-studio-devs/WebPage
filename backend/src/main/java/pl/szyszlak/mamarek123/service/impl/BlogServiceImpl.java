package pl.szyszlak.mamarek123.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szyszlak.mamarek123.model.BlogPostDTO;
import pl.szyszlak.mamarek123.model.BlogPostMapper;
import pl.szyszlak.mamarek123.persistence.entity.BlogPost;
import pl.szyszlak.mamarek123.persistence.repository.BlogRepository;
import pl.szyszlak.mamarek123.service.interfaces.BlogService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private BlogPostMapper blogPostMapper;
    private BlogRepository repository;

    @Override
    public List<BlogPostDTO> getAllPosts() {
        return repository.findAll().stream()
                .map(blogPostMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void savePost(BlogPostDTO postDTO) {
        BlogPost entity = blogPostMapper.toEntity(postDTO);
        repository.save(entity);
    }

    @Override
    public Long countPosts() {
        return repository.count();
    }
}