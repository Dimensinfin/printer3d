package org.dimensinfin.printer3d.backend;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "src/acceptance/resources/features" },
		snippets = CAMELCASE,
		glue = { "org.dimensinfin.printer3d.backend.acceptance.steps" },
		plugin = { "pretty", "json:target/cucumber_report.json" },
		tags = "@B3D01.E.03")
public class RunAcceptanceTests {
}
