package com.elearn.app.start_learn_back.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
        title = "Streaming App Backend",
        description = "Created by DVD & JG",
        version = "1.0V",
        contact = @Contact(
                name ="Team Streaming App",
                email = "streaming@streamingApp.com",
                url = "https://streamingapp.com"
        ),
        license= @License(
                url = "https://streamingapp.com",
                name = "Apace License 2.0")
        ),
        security = @SecurityRequirement(name = "jwtScheme")
)
// we can do this process by creating a simple java bean.
@SecurityScheme(
        name = "jwtScheme",
        type =  SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class ProjectConfig {
 @Bean
    public ModelMapper modelMapper(){
     return new ModelMapper();

 }
}