package com.ecommerce.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {
    public static WebElement visible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.get("explicitWait"))));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void clickableAndClick(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.get("explicitWait"))));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}
