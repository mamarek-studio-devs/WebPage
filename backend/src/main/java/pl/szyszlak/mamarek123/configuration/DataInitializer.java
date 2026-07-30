package pl.szyszlak.mamarek123.configuration;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.szyszlak.mamarek123.model.BlogPostDTO;
import pl.szyszlak.mamarek123.service.interfaces.BlogService;

@Configuration
@AllArgsConstructor
public class DataInitializer {

    BlogService blogService;

    @Bean
    @Profile("dev")
    CommandLineRunner initDatabase() {
        return args -> {
            if (blogService.countPosts() == 0) { // Only seed if empty
                blogService.savePost(BlogPostDTO.builder()
                        .title("First Post")
                        .summary("This is the summary of the first post.")
                        .build());
                blogService.savePost(BlogPostDTO.builder()
                        .title("Second Post")
                        .summary("This is the summary of the second post.")
                        .build());
                blogService.savePost(BlogPostDTO.builder()
                        .title("Third Post")
                        .summary("This is the summary of the third post.")
                        .build());
            }
        };
    }
}