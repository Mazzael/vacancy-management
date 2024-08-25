package br.com.vmazza.vacancy_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Vacancy Management",
		description = "API resposible for vacancy management",
		version = "1"
	)
)
public class VacancyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyManagementApplication.class, args);
	}

}
