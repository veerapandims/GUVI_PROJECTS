package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    private By email = By.id("email");
    private By password = By.id("password");
    private By loginBtn = By.xpath("//button[text()='Submit']");
    private By signupLink = By.xpath("//button[text()='Sign up']");
    private By loginError = By.xpath("//span[text()='Incorrect username or password']");
    private By contactListHeader = By.xpath("//h1[text()='Contact List']");

    public LoginPage() { super(); }

    public void enterEmail(String e) { sendKeys(email, e); }
    public void enterPassword(String p) { sendKeys(password, p); }
    public void clickLogin() { click(loginBtn); }
    public void clickSignUp() { click(signupLink); }

    public boolean isLoginButtonPresent() { return isDisplayed(loginError); }

    public String getValidationMessageForEmail() {
        WebElement emailField = driver.findElement(loginError);
        return emailField.getAttribute("id");
    }
    public String getLoginMessageForEmail() {
        WebElement emailField = driver.findElement(loginError);
        return emailField.getText();
    }
    public String getPasswordFieldType() {
        return driver.findElement(password).getAttribute("type");
    }
    public void waitForVisiblity(){
        waitForVisibility(contactListHeader);
    }
    public void waitForVisiblityError(){
        waitForVisibility(loginError);
    }

    @Override
    public String takeScreenshot(String namePrefix) {
        return super.takeScreenshot(namePrefix);
    }
}

