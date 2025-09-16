package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerSupportPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By contactUsLink = By.xpath("//a[text()='Contact Us']");
    private By nameField = By.id("name");
    private By emailField = By.id("email");
    private By phone = By.id("phone");
    private By messageField = By.id("message");
    private By submitBtn = By.xpath("//input[@value='Send to Customer Care']");
    private By successMessage = By.xpath("//h1[text()='Customer Care']");
    private By errorMessage = By.xpath("//span[text()='Name is required.']");
    private By userName = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//*[@type='submit']");
//    public CustomerSupportPage(WebDriver driver) {
//        super(driver);
//        this.driver = driver;
//    }
    public CustomerSupportPage(WebDriver driver) { super(); }
    public void enterUserName(String userInput) { sendKeys(userName, userInput); }
    public void enterPassword(String pwd) { sendKeys(password, pwd); }
    public void clickLogin() { click(loginBtn); }
    // Actions
    public void clickContactUs() { click(contactUsLink); }
    public void enterName(String name) { sendKeys(nameField, name); }
    public void enterEmail(String email) { sendKeys(emailField, email); }
    public void enterMessage(String message) { sendKeys(messageField, message); }
    public void clickSubmit() { click(submitBtn); }

    public String getSuccessMessage() { return getText(successMessage); }
    public String getErrorMessage() { return getText(errorMessage); }

    public void enterPhone(String message) { sendKeys(phone, message); }
    public String takeScreenshot(String namePrefix) { return super.takeScreenshot(namePrefix); }
}
