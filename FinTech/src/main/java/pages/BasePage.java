package pages;

import core.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    private WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click(By locator) {
        waitForVisibility(locator);
        driver.findElement(locator).click();
    }

    public void jsClick(By locator) {
        waitForVisibility(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(locator));
    }

    public void sendKeys(By locator, String text) {
        waitForVisibility(locator);
        WebElement el = driver.findElement(locator);
        el.clear();
        el.sendKeys(text);
    }

    public String getText(By locator) {
        waitForVisibility(locator);
        return driver.findElement(locator).getText();
    }

    public void selectByVisibleText(By locator, String text) {
        waitForVisibility(locator);
        Select sel = new Select(driver.findElement(locator));
        sel.selectByVisibleText(text);
    }

    public String takeScreenshot(String namePrefix) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") + "/target/screenshots/" + namePrefix + "_" + System.currentTimeMillis() + ".png";
        try {
            FileUtils.copyFile(src, new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest;
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public boolean isDisplayed(By locator) {
        try {
            waitForVisibility(locator);
            return driver.findElement(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
