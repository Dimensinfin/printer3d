package org.dimensinfin.poc.poccucumber.acceptance;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.context.annotation.ComponentScan;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ComponentScan(basePackages = { "org.dimensinfin.poc.poccucumber.acceptance" })
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.dimensinfin.poc.poccucumber.acceptance")
public class CucumberTest {
}