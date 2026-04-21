package steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.junit.Assert;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverManager;

/**
 * Step definitions para cart.feature.
 * Usa anotaciones en español del paquete io.cucumber.java.es.
 */
public class CartSteps {

    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private String addedProductName;

    @Dado("que el usuario ha iniciado sesión como {string}")
    public void userIsLoggedInAs(String username) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.navigateTo();
        loginPage.login(username, "secret_sauce");

        inventoryPage = new InventoryPage(DriverManager.getDriver());
        Assert.assertTrue(
                "El login no redirigió al inventario — revisa las credenciales",
                inventoryPage.isOnInventoryPage()
        );
    }

    @Cuando("agrega el primer producto al carrito")
    public void addFirstProductToCart() {
        addedProductName = inventoryPage.addFirstProductToCart();
        System.out.println("[STEP] Producto agregado: " + addedProductName);
    }

    @Y("navega al carrito")
    public void navigateToCart() {
        inventoryPage.goToCart();
        cartPage = new CartPage(DriverManager.getDriver());
    }

    @Entonces("el producto debe aparecer en el carrito")
    public void verifyProductInCart() {
        Assert.assertTrue(
                "La URL no corresponde a la página del carrito",
                cartPage.isOnCartPage()
        );
        Assert.assertTrue(
                "El producto '" + addedProductName + "' no se encontró en el carrito",
                cartPage.isProductInCart(addedProductName)
        );
    }

    @Entonces("el contador del carrito debe mostrar {int}")
    public void verifyCartCounter(int expectedCount) {
        int actualCount = inventoryPage.getCartBadgeCount();
        Assert.assertEquals(
                "Contador del carrito incorrecto — esperado: " + expectedCount + ", actual: " + actualCount,
                expectedCount,
                actualCount
        );
    }
}
