package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for menu items
    private By menuItemsLocator = By.cssSelector("div.style_menu-container__Ha_wV a.style_menu-item__SLdA4");
    private By menuItemsLocator1 = By.cssSelector("div.style_menu-container__Ha_wV span.style_menu-item__SLdA4");

    // Locator for Markets link
    private By lnkMarkets = By.linkText("Markets");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Fetch all top menu items
    public List<String> getAllMenuItems() {
        List<String> allMenuItems = new ArrayList<>();

        List<WebElement> elements1 = driver.findElements(menuItemsLocator);
        List<WebElement> elements2 = driver.findElements(menuItemsLocator1);

        for (WebElement e : elements1) {
            String text = e.getText().trim();
            if (!text.isEmpty()) allMenuItems.add(text);
        }

        for (WebElement e : elements2) {
            String text = e.getText().trim();
            if (!text.isEmpty()) allMenuItems.add(text);
        }

        return allMenuItems;
    }

    // Click Markets link
    public void clickMarkets() {
        wait.until(ExpectedConditions.elementToBeClickable(lnkMarkets)).click();
    }
}
