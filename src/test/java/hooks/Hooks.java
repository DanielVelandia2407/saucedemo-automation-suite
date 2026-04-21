package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

/**
 * Hooks de Cucumber: inicializa el driver antes de cada escenario
 * y lo cierra después, adjuntando screenshot si el escenario falla.
 */
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("[BEFORE] Iniciando escenario: " + scenario.getName());
        // Inicializa el driver (Chrome headless) via DriverManager
        DriverManager.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed() && driver != null) {
            System.out.println("[AFTER] Escenario fallido: " + scenario.getName() + " — capturando screenshot");
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Fallo en: " + scenario.getName());
            } catch (Exception e) {
                System.err.println("[AFTER] No se pudo capturar screenshot: " + e.getMessage());
            }
        }

        System.out.println("[AFTER] Cerrando navegador para: " + scenario.getName());
        DriverManager.quitDriver();
    }
}
