package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver initDriver(String browser) {
        WebDriver driver;
        if (browser == null) browser = "chrome";
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fo = new FirefoxOptions();
                driver = new FirefoxDriver(fo);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions eo = new EdgeOptions();
                driver = new EdgeDriver(eo);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                co.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(co);
                break;
        }
        tlDriver.set(driver);
        return driver;
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public static synchronized void quitDriver() {
        WebDriver d = tlDriver.get();
        if (d != null) {
            d.quit();
            tlDriver.remove();
        }
    }
}
