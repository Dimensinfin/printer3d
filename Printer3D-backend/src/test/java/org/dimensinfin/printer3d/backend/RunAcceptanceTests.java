package org.dimensinfin.printer3d.backend;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
		strict = true,
		features = { "src/test/resources/features" },
		snippets= CAMELCASE,
		glue = { "org.dimensinfin.printer3d.backend.steps" },
		plugin = { "pretty", "json:build/cucumber_report.json" },
		tags = { "not @skip_scenario", "not @front", "not @duplication", "@B3D10.11" })
public class RunAcceptanceTests {}
