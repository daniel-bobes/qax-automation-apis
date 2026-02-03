package com.bobes.daniel.api.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

// Marca la clase como una suite de pruebas reconocida por JUnit
@Suite
// Indica a JUnit que utilice el motor de ejecución de Cucumber
@IncludeEngines("cucumber")
// Especifica la ubicación de los archivos ".feature" dentro de resources
@SelectClasspathResource("features")
// Define el paquete donde se ubican las clases que implementan los pasos de los escenarios
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.bobes.daniel.api.steps")
// Configura los plugins de reporte, salida legible (pretty, summary) y reportes en HTML y JSON en la carpeta target
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "pretty, summary, html:target/cucumber-reports/main-report.html, json:target/cucumber-reports/main-report.json")
public class AllTestRunner {

}
