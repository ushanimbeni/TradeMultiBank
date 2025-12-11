package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DashboardPage;
import utils.JsonReader;

import java.util.List;

public class DashboardTests extends BaseTest {

    @Test
    public void verifyMenuItems() {
        // Initialize Dashboard page
        DashboardPage dashboard = new DashboardPage(driver);

        // Fetch actual menu items from dashboard
        List<String> actualMenuItems = dashboard.getAllMenuItems();

        // Fetch expected menu items from JSON
        List<String> expectedMenuItems = JsonReader.readTopMenuItems();

        // Log to ExtentReports
        extentTest.get().info("Expected Menu Items: " + expectedMenuItems);
        extentTest.get().info("Actual Menu Items  : " + actualMenuItems);

        // Soft assertion to verify each menu item
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < expectedMenuItems.size(); i++) {
            if (i < actualMenuItems.size()) {
                softAssert.assertEquals(actualMenuItems.get(i), expectedMenuItems.get(i),
                        "Mismatch at position " + (i + 1));
            } else {
                softAssert.fail("Missing menu item: " + expectedMenuItems.get(i));
            }
        }

        // Extra check if dashboard has extra menu items
        if (actualMenuItems.size() > expectedMenuItems.size()) {
            for (int i = expectedMenuItems.size(); i < actualMenuItems.size(); i++) {
                softAssert.fail("Extra menu item found: " + actualMenuItems.get(i));
            }
        }

        softAssert.assertAll();
    }
}
