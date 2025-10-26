package com.ecommerce.pages;

import com.ecommerce.core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderModal {
    private final WebDriver driver;
    private final By name = By.id("name");
    private final By country = By.id("country");
    private final By city = By.id("city");
    private final By card = By.id("card");
    private final By month = By.id("month");
    private final By year = By.id("year");
    private final By purchaseBtn = By.xpath("//button[text()='Purchase']");
    private final By closeBtn = By.xpath("//button[text()='Close']");
    private final By okBtn = By.xpath("//button[text()='OK']");

    public OrderModal(WebDriver driver){ this.driver = driver; }

    public void fill(String n, String cty, String ci, String cd, String m, String y){
        Waits.visible(driver, name).sendKeys(n);
        driver.findElement(country).sendKeys(cty);
        driver.findElement(city).sendKeys(ci);
        driver.findElement(card).sendKeys(cd);
        driver.findElement(month).sendKeys(m);
        driver.findElement(year).sendKeys(y);
    }

    public void clickPurchase(){ Waits.clickableAndClick(driver, purchaseBtn); }

    public boolean confirmationVisible(){
        try {
            WebElement el = Waits.visible(driver, okBtn);
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public void confirm(){ Waits.clickableAndClick(driver, okBtn); }
    public void close(){ Waits.clickableAndClick(driver, closeBtn); }
}
