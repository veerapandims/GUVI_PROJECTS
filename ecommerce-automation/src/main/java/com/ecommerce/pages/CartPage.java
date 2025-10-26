package com.ecommerce.pages;

import com.ecommerce.core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    private final WebDriver driver;
    private final By deleteLinks = By.linkText("Delete");
    private final By totalPrice = By.id("totalp");
    private final By placeOrderBtn = By.xpath("//button[text()='Place Order']");

    public CartPage(WebDriver driver){ this.driver = driver; }

    public int itemsCount(){
        List<WebElement> dels = driver.findElements(deleteLinks);
        return dels.size();
    }

    public void deleteFirstItem(){
        List<WebElement> dels = driver.findElements(deleteLinks);
        if(!dels.isEmpty()) dels.get(0).click();
    }

    public int getTotal(){
        String t = Waits.visible(driver, totalPrice).getText();
        try { return Integer.parseInt(t.trim()); } catch (Exception e) { return 0; }
    }

    public void clickPlaceOrder(){
        Waits.clickableAndClick(driver, placeOrderBtn);
    }
}
