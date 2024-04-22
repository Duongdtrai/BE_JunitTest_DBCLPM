package com.example.junit_test.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ApiDocumentationConfig {
    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .servers(Arrays.asList(
                        new Server().url("http://54.199.68.197:8081"),
                        new Server().url("https://4090-1-52-109-9.ngrok-free.app"),
                        new Server().url("http://localhost:8081")
                ))
                .info(new Info()
                        .title("API Quan Ly Ban Hang")
                        .description("API QLBH")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Phạm Tùng Dương")
                                .email("phamtungduong06032002@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Profile developer")
                        .url("https://www.facebook.com/PhamTungDuong63/"));
    }
}
