package com.ecommerce.pages;

import com.ecommerce.core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;

public class LoginPage {
    private final WebDriver driver;
    private final By username = By.id("loginusername");
    private final By password = By.id("loginpassword");
    private final By loginBtn = By.xpath("//button[text()='Log in']");

    public LoginPage(WebDriver driver){ this.driver = driver; }

    public void login(String user, String pass){
        Waits.visible(driver, username).clear();
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
        Waits.clickableAndClick(driver, loginBtn);
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
