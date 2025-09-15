package listeners;

import core.DriverFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import pages.BasePage;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(
                    System.getProperty("user.dir") + "/target/extent-report.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String path = null;
        try {
            if (DriverFactory.getDriver() != null) {
                path = new BasePage().takeScreenshot(result.getMethod().getMethodName());
            }
            if (path != null) {
                test.get().fail(result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(path).build());
            } else {
                test.get().fail(result.getThrowable());
            }
        } catch (Exception e) {
            test.get().fail("Screenshot attach failed");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}
