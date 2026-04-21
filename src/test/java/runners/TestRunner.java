package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner de Cucumber con JUnit 4.
 *
 * Para filtrar por tag desde Maven:
 *   mvn test -Dcucumber.filter.tags="@smoke"
 *   mvn test -Dcucumber.filter.tags="@regression"
 *   mvn test -Dcucumber.filter.tags="@smoke or @regression"
 *
 * Cucumber 7 lee la propiedad del sistema "cucumber.filter.tags" automáticamente,
 * lo que permite que surefire la inyecte sin hardcodearla aquí.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                // Allure: genera resultados en target/allure-results
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class TestRunner {
    // Clase vacía: JUnit 4 + Cucumber gestionan la ejecución
}
