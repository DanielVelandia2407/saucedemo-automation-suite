package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Singleton thread-safe para gestionar el ciclo de vida del WebDriver.
 * Usa ThreadLocal para soportar ejecución paralela sin conflictos.
 * Selenium Manager (incluido en Selenium 4.6+) descarga el driver automáticamente.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
        // Clase utilitaria, no instanciar
    }

    /**
     * Retorna el WebDriver existente o crea uno nuevo en modo headless.
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            ChromeOptions options = new ChromeOptions();
            // Headless por defecto; pasar -Dheadless=false para ver el navegador localmente
            if (!"false".equalsIgnoreCase(System.getProperty("headless", "true"))) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--remote-allow-origins=*");
            driver.set(new ChromeDriver(options));
        }
        return driver.get();
    }

    /**
     * Cierra el navegador y elimina la instancia del ThreadLocal.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
