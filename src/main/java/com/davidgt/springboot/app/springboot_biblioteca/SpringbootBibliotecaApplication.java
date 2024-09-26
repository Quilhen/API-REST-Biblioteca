package com.davidgt.springboot.app.springboot_biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class SpringbootBibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBibliotecaApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenApi(){
		return new OpenAPI()
					.info(new Info()
					.title("Spring Boot 3 API")
					.description("API REST biblioteca")
					.termsOfService("http://swagger.io/terms/")
					.license(new License().name("Apache 2.0").url("http://springdoc.org")));
					
	}

}
