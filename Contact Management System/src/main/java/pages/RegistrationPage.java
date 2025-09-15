package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {
    private By clickSignUP = By.id("signup");
    private By firstNameInput = By.id("firstName");
    private By lastNameInput = By.id("lastName");
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By signUpButton = By.id("submit");
    private By successMessage = By.xpath("//p[text()='Click on any contact to view the Contact Details']");
    private By existseErrorMessage = By.id("//span[text()='Email address is already in use']");
    private By requiredFieldMessage = By.id("error");

    public void clickSignup() {
      click(clickSignUP);
    }

    public void enterfName(String fName) {
        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(fName);
    }
    public void enterlName(String lName) {
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }


    public void clickSignUp() {
        driver.findElement(signUpButton).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }

    public String getExistsErrorMessage() {
        return driver.findElement(existseErrorMessage).getText();
    }

    public String getRequiredFieldMessage() {
        return driver.findElement(requiredFieldMessage).getText();
    }
}
