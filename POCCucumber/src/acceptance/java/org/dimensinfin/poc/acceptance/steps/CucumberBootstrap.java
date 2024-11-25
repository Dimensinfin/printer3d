package org.dimensinfin.poc.acceptance.steps;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import org.dimensinfin.Printer3DApplication;
import org.dimensinfin.poc.acceptance.CucumberLauncherAcc;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(name = "steps", classes = { CucumberLauncherAcc.class })
@SpringBootTest(classes = { Printer3DApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@ActiveProfiles({ "acceptance" })
@EnableAutoConfiguration
public class CucumberBootstrap {
}
