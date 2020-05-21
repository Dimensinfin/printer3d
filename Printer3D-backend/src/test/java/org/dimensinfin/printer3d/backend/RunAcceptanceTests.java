package org.innovative.dimension.innodental.backend;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "src/test/resources/features" },
		snippets= CAMELCASE,
		glue = { "org.innovative.dimension.innodental.backend.steps" },
		plugin = { "pretty", "json:target/cucumber_report.json" },
		tags = { "not @skip_scenario", "not @front", "not @duplication", "@ID02.H" })
public class RunAcceptanceTests {}
