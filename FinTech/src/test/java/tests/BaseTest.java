package tests;

import core.DriverFactory;
import managers.PageObjectManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.*;

import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BaseTest {
    protected PageObjectManager pages;
    protected static ExtentReports extent;
    protected ExtentTest test;
    protected WebDriver driver;


    public void BasePage() {
        this.driver = DriverFactory.getDriver();  // no constructor with driver
    }

    @BeforeSuite
    public void beforeSuite() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/target/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.initDriver(browser);
        pages = new PageObjectManager(driver);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {
        if (extent != null) extent.flush();
    }

    public String takeScreenshot(String namePrefix) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir") + "/screenshots/" + namePrefix + ".png";
            File dest = new File(path);
            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath());
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
