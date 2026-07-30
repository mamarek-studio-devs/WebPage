package pl.szyszlak.mamarek123.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blogApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mamarek123 Blog API")
                        .description("REST API for managing blog posts")
                        .version("1.0.0"));
    }
}
