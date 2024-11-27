package org.dimensinfin.poc.acceptance.steps;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import org.dimensinfin.poc.poccucumber.POCCucumberApp;
import org.dimensinfin.poc.acceptance.CucumberLauncherAcc;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(name = "steps", classes = { CucumberLauncherAcc.class })
@SpringBootTest(classes = { POCCucumberApp.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@ActiveProfiles({ "acceptance" })
@EnableAutoConfiguration
public class CucumberBootstrap {
}
