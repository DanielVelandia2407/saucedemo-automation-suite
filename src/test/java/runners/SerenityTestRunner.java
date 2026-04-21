package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Runner de Serenity BDD con Cucumber 7.
 *
 * Reemplaza @RunWith(Cucumber.class) por @RunWith(CucumberWithSerenity.class).
 * Serenity escucha los eventos de Cucumber y genera su propio reporte HTML.
 *
 * Activar con el perfil Maven "serenity":
 *   mvn verify -Pserenity
 *   mvn verify -Pserenity "-Dcucumber.filter.tags=@smoke"
 *
 * Reporte generado en: target/site/serenity/index.html
 *
 * Diferencias clave vs TestRunner estándar:
 *  - El driver lo sigue gestionando nuestro DriverManager (Hooks.java).
 *  - Serenity actúa como capa de reporte encima de Cucumber.
 *  - No se necesita @Steps ni @Managed: es reporte puro.
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        plugin = {"pretty"},
        monochrome = true
)
public class SerenityTestRunner {
    // Clase vacía: CucumberWithSerenity gestiona la ejecución
}
