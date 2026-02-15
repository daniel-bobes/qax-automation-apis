package com.danielbobes.api.challenges.bookStore.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/bookStore")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.danielbobes.api.challenges.bookStore.steps")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "pretty, summary, html:target/cucumber-reports/main-report.html, json:target/cucumber-reports/main-report.json")
public class MainRunner { }
