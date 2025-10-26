package com.ecommerce.core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ecommerce.reports.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(@Optional("chrome") String browser) {
        extent = ExtentManager.getInstance();
    }

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, java.lang.reflect.Method method) {
        driver = DriverFactory.create(browser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.get("implicitWait"))));
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("baseUrl"));
        test = extent.createTest(getClass().getSimpleName() + "::" + method.getName() + " [" + browser + "]");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String path = takeScreenshot();
            test.fail(result.getThrowable());
            if (path != null) test.addScreenCaptureFromPath(path);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Skipped");
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        if (extent != null) extent.flush();
    }

    private String takeScreenshot() {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String folder = ConfigReader.get("screenshotPath");
            new File(folder).mkdirs();
            String path = folder + "/" + UUID.randomUUID() + ".png";
            Files.copy(src.toPath(), Path.of(path));
            return path;
        } catch (IOException e) {
            return null;
        }
    }
}
