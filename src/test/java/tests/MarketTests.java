package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DashboardPage;
import pages.MarketPage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarketTests extends BaseTest {

    private DashboardPage dashboardPage;
    private MarketPage marketPage;

    @Test
    public void verifyMarketTopList() {
        SoftAssert softAssert = new SoftAssert();

        dashboardPage = new DashboardPage(driver);
        marketPage = new MarketPage(driver);

        // Open Markets page
        dashboardPage.clickMarkets();
        marketPage.handlePopupIfAny(); // method in MarketPage to handle notification popup

        // Fetch top stocks and symbols using JS executor
        marketPage.fetchTopStocksAndSymbols();
        List<String> topStocks = marketPage.getTopStocks();
        List<String> topSymbols = marketPage.getTopSymbols();

        extentTest.get().info("Top Stocks (Top List)   : " + topStocks);
        extentTest.get().info("Top Symbols (Top List)  : " + topSymbols);

        // Fetch table stocks and symbols
        List<WebElement> tableRows = driver.findElements(By.cssSelector("div.style_coin-names__FOITQ"));
        List<String> tableStocks = new ArrayList<>();
        List<String> tableSymbols = new ArrayList<>();

        for (WebElement row : tableRows) {
            String stock = row.findElement(By.xpath("./label[1]")).getText().trim();
            String symbol = row.findElement(By.xpath("./label[2]")).getText().trim();
            tableStocks.add(stock);
            tableSymbols.add(symbol);
        }

        extentTest.get().info("Top Stocks (Table List) : " + tableStocks);
        extentTest.get().info("Top Symbols (Table List): " + tableSymbols);

        // Compare lists ignoring sequence
        Set<String> topStocksSet = new HashSet<>(topStocks);
        Set<String> tableStocksSet = new HashSet<>(tableStocks);

        Set<String> topSymbolsSet = new HashSet<>(topSymbols);
        Set<String> tableSymbolsSet = new HashSet<>(tableSymbols);

        softAssert.assertEquals(topStocksSet, tableStocksSet, "Stocks mismatch (ignoring order)!");
        softAssert.assertEquals(topSymbolsSet, tableSymbolsSet, "Symbols mismatch (ignoring order)!");

        extentTest.get().info("Comparison completed: Top List vs Table List (order ignored)");

        softAssert.assertAll();
    }
}
