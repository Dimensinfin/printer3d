package org.dimensinfin.poc.poccucumber.acceptance.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import org.dimensinfin.poc.poccucumber.POCCucumberApp;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(name = "steps", classes = { POCCucumberApp.class })
@SpringBootTest(classes = { POCCucumberApp.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({ "acceptance" })
@EnableAutoConfiguration
public class CucumberSpringConfiguration {
	@Autowired
	public static TestRestTemplate testRestTemplate;
}