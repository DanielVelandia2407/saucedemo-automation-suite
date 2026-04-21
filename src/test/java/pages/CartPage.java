package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object para la página del carrito de compras de SauceDemo.
 */
public class CartPage {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart");
    }

    public int getCartItemCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.className("cart_item")));
            return cartItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isProductInCart(String productName) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.className("cart_item")));
            return cartItems.stream()
                    .anyMatch(item -> item.getText().contains(productName));
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
        continueShoppingButton.click();
    }

    public List<WebElement> getCartItems() {
        return cartItems;
    }
}
