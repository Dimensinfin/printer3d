package org.dimensinfin.poc.poccucumber.acceptance;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@ComponentScan(basePackages = "org.dimensinfin.poc.poccucumber.acceptance") // Adjust the package as necessary
public class TestConfig {
	// This class can be empty, it serves to load the Spring context
}