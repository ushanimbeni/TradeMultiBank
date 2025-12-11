**Tests Overview
****Dashboard Tests**

**Purpose: Verify the top menu items on the dashboard.
**
How it works:

Fetches all visible menu items from the dashboard page.

Compares them against expected menu items loaded from a JSON file.

Uses soft assertions to report all mismatches instead of stopping at the first failure.

Logs expected vs actual menu items in ExtentReports for easy debugging.

**Market Tests
**
**Purpose: Validate the list of top cryptocurrencies on the Markets page.
**
How it works:

Opens the Markets page from the dashboard.

Handles any popups (notifications) that appear on page load.

Fetches the Top List of stocks and symbols using JavaScriptExecutor to get accurate text content.

Fetches the Table List of stocks and symbols directly from the table.

Compares the top list and table list for data correctness (order can be ignored).

Logs all stock and symbol comparisons in ExtentReports.

Page Object Model (POM)

DashboardPage: Handles actions and locators for the dashboard page, such as fetching menu items and navigating to Markets.

MarketPage: Handles actions and locators for the Markets page, including:

Fetching top stocks and symbols using JavaScriptExecutor.

Handling notification popups.

Fetching table stocks and symbols for comparison.

Benefits of POM:

Improves maintainability by separating page actions from test logic.

Makes test scripts cleaner and more readable.

Allows reusability of page methods across multiple test classes.

Utilities

JsonReader: Reads expected menu items or other test data from JSON files.

ExtentReportManager: Configures and manages ExtentReports for logging test steps and results.
