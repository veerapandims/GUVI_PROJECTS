package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditContactPage extends BasePage {

    // 🔑 Remove the redundant private WebDriver driver;
    // BasePage already has a protected driver

    // Locators
    private By editButton = By.xpath("(//td[text()='sachin dendulkar'])[1]");
    private By emailField = By.id("email");
    private By phoneField = By.id("phone");
    private By lastNameField = By.id("lastName");
    private By saveButton = By.id("submit");
    private By cancelButton = By.id("cancel");
    private By errorMessage = By.id("error");
    // Login fields (if this page handles login too)
    private By email = By.id("email");
    private By password = By.id("password");
    private By loginBtn = By.xpath("//button[text()='Submit']");
//    public EditContactPage(WebDriver driver) {
//        super(driver);
//    }

    // Actions
    public void enterEmail(String e) {
        sendKeys(email, e);
    }

    public void enterPassword(String p) {
        sendKeys(password, p);
    }

    public void clickLogin() {
        click(loginBtn);
    }

    public void clickEdit() {
        driver.findElement(editButton).click();
    }

    public void updateEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void updatePhone(String phone) {
        driver.findElement(phoneField).clear();
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clearLastName() {
        driver.findElement(lastNameField).clear();
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public String takeScreenshot(String namePrefix) {
        return super.takeScreenshot(namePrefix);
    }
}
