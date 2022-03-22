package fr.example.technicaloffer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI(@Value("${app.api.description}") String apiDesciption,
			@Value("${app.api.title}") String apiTitle, @Value("${app.api.version}") String apiVersion) {
		return new OpenAPI().info(new Info().title(apiTitle).version(apiVersion).description(apiDesciption));
	}

}
