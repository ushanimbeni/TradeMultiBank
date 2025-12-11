package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;

public class ExtentListener implements ITestListener {

    private static ExtentReports extent = ExtentReportManager.getInstance();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().pass("Test Passed");
        logMenuItems(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThread.get().fail(result.getThrowable());
        logMenuItems(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    // Log actual and expected menu items if passed as test parameters
    private void logMenuItems(ITestResult result) {
        Object[] params = result.getParameters();
        if (params != null && params.length == 2) {
            List<String> actual = (List<String>) params[0];
            List<String> expected = (List<String>) params[1];

            testThread.get().info("Expected Menu Items: " + expected);
            testThread.get().info("Actual Menu Items  : " + actual);
        }
    }
}
