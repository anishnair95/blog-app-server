package com.springboot.blog;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
public class SpringbootBlogRestApiApplication implements CommandLineRunner {

    @Autowired
    public RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        persistUserRolesIfNotExist();
    }

    private void persistUserRolesIfNotExist() {
        List<Role> roles = roleRepository.findAll();
        List<Role> rolesToSave = new ArrayList<>();

        Map<String, Boolean> userRoles = roles.stream().filter(Objects::nonNull)
                .filter(r -> r.getName().equals("ROLE_USER") || r.getName().equals("ROLE_ADMIN"))
                .collect(Collectors.toMap(Role::getName, r -> true));

        if (!userRoles.containsKey("ROLE_USER")) {
            rolesToSave.add(new Role(1L, "ROLE_USER"));
        }

        if (!userRoles.containsKey("ROLE_ADMIN")) {
            rolesToSave.add(new Role(2L, "ROLE_ADMIN"));
        }

        if (!rolesToSave.isEmpty()) {
            roleRepository.saveAll(rolesToSave);
        }
    }
}
