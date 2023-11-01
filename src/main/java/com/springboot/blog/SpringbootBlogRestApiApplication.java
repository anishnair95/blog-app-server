package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
        // add all information
        title = "Spring Boot Blog APP REST APIs",
        description = "Spring Boot Blog APP REST APIs documentation",
        version = "v1.0",
        contact = @Contact(
                name = "Anish Nair",
                email = "anishnair95@gmail.com",
                url = "https://github.com/anishnair95"
        ),
        // add license information
        license = @License(
                name = "Apache 2.0",
                url = "https://github.com/anishnair95"
        )),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog App Documentation",
                url = "https://github.com/anishnair95/blog-app-server"
        )
)
public class SpringbootBlogRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
    }

}
