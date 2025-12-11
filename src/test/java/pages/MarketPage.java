package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MarketPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//a[normalize-space()='Markets']")
    WebElement lnkMarkets;

    @FindBy(css = "div.style_crypto-name-container__bUPTc > span.style_crypto-title__1CBJd")
    List<WebElement> topStocksElements;

    @FindBy(css = "div.style_crypto-name-container__bUPTc > span.style_crypto-slug__eFVdD")
    List<WebElement> topSymbolsElements;

    By popupCloseBtn = By.cssSelector("button.style_modal-close__2xYjv");

    List<String> topStocks = new ArrayList<>();
    List<String> topSymbols = new ArrayList<>();

    public MarketPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ✔ Handle popup safely
    public void handlePopup() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(popupCloseBtn)).click();
        } catch (Exception ignored) {}
    }

    // ✔ Open Markets + dismiss popup
    public void openMarkets() {
        wait.until(ExpectedConditions.elementToBeClickable(lnkMarkets)).click();
        handlePopup();
    }

    // ✔ Fetch top list using JavaScript (NOT visibility wait)
    public void fetchTopStocksAndSymbols() {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("div.style_crypto-name-container__bUPTc")));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        topStocks.clear();
        topSymbols.clear();

        for (WebElement ele : topStocksElements) {
            String stock = js.executeScript(
                    "return arguments[0].textContent;", ele).toString().trim();
            topStocks.add(stock);
        }

        for (WebElement ele : topSymbolsElements) {
            String symbol = js.executeScript(
                    "return arguments[0].textContent;", ele).toString().trim();
            topSymbols.add(symbol);
        }
    }

    public List<String> getTopStocks() { return topStocks; }

    public List<String> getTopSymbols() { return topSymbols; }

    // ✔ Fetch table list (normal Selenium)
    public List<String> getTableStocks() {
        List<String> stocks = new ArrayList<>();

        List<WebElement> rows = driver.findElements(By.cssSelector("div.style_coin-names__FOITQ"));

        for (WebElement row : rows) {
            stocks.add(row.findElement(By.xpath("./label[1]")).getText().trim());
        }

        return stocks;
    }

    public List<String> getTableSymbols() {
        List<String> symbols = new ArrayList<>();

        List<WebElement> rows = driver.findElements(By.cssSelector("div.style_coin-names__FOITQ"));

        for (WebElement row : rows) {
            symbols.add(row.findElement(By.xpath("./label[2]")).getText().trim());
        }

        return symbols;
    }

    public void handlePopupIfAny() {
        try {
            WebElement popupCloseBtn = driver.findElement(By.cssSelector("div.popup-close, button.close")); // adjust selector to your popup
            if (popupCloseBtn.isDisplayed()) {
                popupCloseBtn.click();
            }
        } catch (Exception e) {
            // Popup not present, ignore
        }
    }
}
