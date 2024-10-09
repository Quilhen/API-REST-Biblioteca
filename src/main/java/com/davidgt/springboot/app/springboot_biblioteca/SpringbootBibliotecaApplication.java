package com.davidgt.springboot.app.springboot_biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@SpringBootApplication
public class SpringbootBibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBibliotecaApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenApi(){
		return new OpenAPI()
					.info(new Info()
					.title("API de Biblioteca")
					.description("API REST biblioteca")
					.termsOfService("http://swagger.io/terms/")
					.license(new License().name("Apache 2.0").url("http://springdoc.org")))
					.addTagsItem(new Tag().name("Login").description("Aqui se realiza el login."))
					.addTagsItem(new Tag().name("Usuarios").description("Operaciones relacionadas con los usuarios."))
					.addTagsItem(new Tag().name("Libros").description("Operaciones relacionadas con los libros."))
					.addTagsItem(new Tag().name("Prestamos").description("Operaciones relacionadas con los prestamos."))
					.components(new Components()
						.addSecuritySchemes("bearerAuth", new SecurityScheme()
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")
							.in(SecurityScheme.In.HEADER)
							.name("Authorization")))
					.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
					
	}

}
