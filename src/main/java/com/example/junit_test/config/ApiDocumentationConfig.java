package com.example.junit_test.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {
  @Bean
  public OpenAPI apiDocConfig() {
    return new OpenAPI()
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
