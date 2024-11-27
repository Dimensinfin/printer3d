package org.dimensinfin.poc.poccucumber;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * This is the spring boot application starter. Once the application instance is created then the spring boot will create all the beans and
 * components.
 *
 * @author Adam Antinoo
 * @since 0.9.0
 */
@Slf4j
@SpringBootApplication
public class POCCucumberApp {
	public static final String APPLICATION_ERROR_CODE_PREFIX = "dimensinfin.poccucumber";

	public static void main( final String[] args ) {
		SpringApplication.run( POCCucumberApp.class, args );
	}
}
