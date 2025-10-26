package com.ecommerce.pages;

import com.ecommerce.core.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;

public class SignupPage {
    private final WebDriver driver;
    private final By username = By.id("sign-username");
    private final By password = By.id("sign-password");
    private final By signUpBtn = By.xpath("//button[text()='Sign up']");

    public SignupPage(WebDriver driver){ this.driver = driver; }

    public void signUp(String user, String pass){
        Waits.visible(driver, username).clear();
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
        Waits.clickableAndClick(driver, signUpBtn);
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
