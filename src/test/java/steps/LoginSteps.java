package steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import org.junit.Assert;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverManager;

/**
 * Step definitions para login.feature.
 * Usa anotaciones en español del paquete io.cucumber.java.es.
 */
public class LoginSteps {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @Dado("que el usuario navega a la página de login")
    public void navigateToLoginPage() {
        loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.navigateTo();
    }

    @Cuando("ingresa el usuario {string} y la contraseña {string}")
    public void enterCredentials(String username, String password) {
        loginPage.login(username, password);
    }

    @Cuando("no ingresa ningún dato")
    public void noDataEntered() {
        loginPage.clickLogin();
    }

    @Entonces("debe ser redirigido al inventario de productos")
    public void verifyRedirectionToInventory() {
        inventoryPage = new InventoryPage(DriverManager.getDriver());
        Assert.assertTrue(
                "No se redirigió al inventario de productos",
                inventoryPage.isOnInventoryPage()
        );
    }

    @Entonces("debe ver el mensaje de error {string}")
    public void verifyErrorMessage(String expectedMessage) {
        Assert.assertTrue(
                "El mensaje de error no es visible",
                loginPage.isErrorDisplayed()
        );
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(
                "Mensaje esperado: '" + expectedMessage + "' — Mensaje actual: '" + actualMessage + "'",
                actualMessage.contains(expectedMessage)
        );
    }
}
