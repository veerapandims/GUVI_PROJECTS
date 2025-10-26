package com.ecommerce.pages;

import com.ecommerce.core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;

public class ProductPage {
    private final WebDriver driver;
    private final By addToCart = By.linkText("Add to cart");

    public ProductPage(WebDriver driver){ this.driver = driver; }

    public void addProductToCart(){
        Waits.clickableAndClick(driver, addToCart);
    }

    public String readAlertIfPresent() {
        try {
            Alert a = driver.switchTo().alert();
            String msg = a.getText();
            a.accept();
            return msg;
        } catch (Exception e) { return null; }
    }
}
