package com.guillemjimenez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.guillemjimenez.model")
public class Uf4RecuperacioApplication {
	public static void main(String[] args) {
		SpringApplication.run(Uf4RecuperacioApplication.class, args);
	}
}
