package com.clinica.odontologica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class OdontologicaApplication {

	private static final Logger logger = LogManager.getLogger(OdontologicaApplication.class);
	public static void main(String[] args) {
		logger.info("Iniciando aplicación de gestión de clínica odontológica");
		SpringApplication.run(OdontologicaApplication.class, args);
	}

}
