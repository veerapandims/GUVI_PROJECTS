package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.testng.internal.MethodHelper.isEnabled;

public class NavigationUIPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By homeLink = By.xpath("//a[text()='Home']");
    private By accountsLink = By.xpath("//a[text()='Accounts Overview']");
    private By transferLink = By.xpath("//a[text()='Transfer Funds']");
    private By loanLink = By.xpath("//a[text()='Request Loan']");
    private By contactLink = By.xpath("//a[text()='Contact Us']");
    private By logo = By.xpath("//img[@alt='ParaBank']");

    public By submitBtn = By.id("submit");
    public By transferBtn = By.id("transfer");
    private By alertBox = By.id("alert");
    private By toastMsg = By.id("toast");

//    public NavigationUIPage(WebDriver driver) {
//        super(driver);
//        this.driver = driver;
//    }
    public NavigationUIPage(WebDriver driver) { super(); }

    // Navigation
    public void clickHome() { click(homeLink); }
    public void clickAccounts() { click(accountsLink); }
    public void clickTransfer() { click(transferLink); }
    public void clickLoan() { click(loanLink); }
    public void clickContact() { click(contactLink); }
    public void clickLogo() { click(logo); }
    private By userName = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//*[@type='submit']");
    // UI Checks
    public boolean isButtonVisible(By locator) { return isDisplayed(locator); }
//    public boolean isButtonClickable(By locator) { return isEnabled(locator); }

    public boolean isFontConsistent(WebElement element) {
        String font = element.getCssValue("font-family");
        return font.contains("Arial") || font.contains("Roboto"); // Example check
    }

    public boolean isAligned(WebElement element) {
        return element.isDisplayed() && element.getLocation().getX() > 0;
    }

    // Alerts and Toasts
    public boolean isAlertVisible() { return isDisplayed(alertBox); }
    public String getAlertText() { return getText(alertBox); }
    public boolean isToastVisible() { return isDisplayed(toastMsg); }
    public String getToastMessage() { return getText(toastMsg); }

    public String takeScreenshot(String namePrefix) { return super.takeScreenshot(namePrefix); }

    public boolean isButtonClickable(By transferBtn) {
        return false;
    }
    public void enterUserName(String userInput) { sendKeys(userName, userInput); }
    public void enterPassword(String pwd) { sendKeys(password, pwd); }
    public void clickLogin() { click(loginBtn); }
}
