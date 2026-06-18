package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object para la página de inventario (catálogo de productos) de SauceDemo.
 */
public class InventoryPage {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory");
    }

    /**
     * Agrega el primer producto de la lista al carrito.
     * @return nombre del producto agregado
     */
    public String addFirstProductToCart() {
        wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));
        WebElement firstItem = inventoryItems.get(0);
        String productName = firstItem.findElement(By.className("inventory_item_name")).getText();
        WebElement addButton = firstItem.findElement(By.cssSelector("button[data-test^='add-to-cart']"));
        jsClick(addButton);
        // Confirma que el click se registró antes de continuar (en headless el click
        // sintético puede perderse; esperar el badge garantiza determinismo).
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".shopping_cart_badge")));
        return productName;
    }

    public String getFirstProductName() {
        wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));
        return inventoryItems.get(0)
                .findElement(By.className("inventory_item_name"))
                .getText();
    }

    /**
     * Retorna el número que muestra el badge del carrito. 0 si no hay badge visible.
     */
    public int getCartBadgeCount() {
        try {
            WebElement badge = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".shopping_cart_badge")));
            return Integer.parseInt(badge.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public void goToCart() {
        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.shopping_cart_link")));
        jsClick(cartLink);
        wait.until(ExpectedConditions.urlContains("/cart.html"));
    }

    /**
     * Click vía JavaScript: dispara el manejador onClick de React directamente,
     * evitando que el click sintético se pierda en Chrome headless (Linux/CI).
     */
    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
