package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FundsTransferPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By fromAccountDropdown = By.id("fromAccountId");
    private By toAccountDropdown = By.id("toAccountId");
    private By amountField = By.id("amount");
    private By transferBtn = By.xpath("//input[@value='Transfer']");
    private By confirmationMessage = By.xpath("//h1[text()='Transfer Complete!']");
    private By errorMessage = By.xpath("//*[normalize-space(text())='An internal error has occurred and has been logged.']");
    private By userName = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//*[@type='submit']");
    private By clickFunTrans = By.xpath("//a[text()='Transfer Funds']");
    private By loadRequestLink = By.xpath("//a[text()='Request Loan']");
//    public FundsTransferPage(WebDriver driver) {
//        super(driver);
//        this.driver = driver;
//    }

    public FundsTransferPage(WebDriver driver) { super(); }

    public void enterUserName(String userInput) { sendKeys(userName, userInput); }
    public void enterPassword(String pwd) { sendKeys(password, pwd); }
    public void clickLogin() { click(loginBtn); }
    public void clickFundTransferLink() { click(clickFunTrans); }
    public boolean isAccountSummaryVisible() { return isDisplayed(accountSummaryHeader); }
    // Actions
    private By accountSummaryHeader = By.xpath("//h1[normalize-space()='Accounts Overview']");
    public void selectFromAccount(String from) {
        click(fromAccountDropdown);
        click(fromAccountDropdown);}
    public void selectToAccount(String to) { click(toAccountDropdown);
        click(toAccountDropdown);}
    public void enterAmount(String amount) { sendKeys(amountField, amount); }
    public void clickTransfer() { click(transferBtn); }
    public void clickLoadRequestLink() { click(loadRequestLink); }
    public String getConfirmationMessage() { return getText(confirmationMessage); }
    public String getErrorMessage() { return getText(errorMessage); }

    public String takeScreenshot(String namePrefix) { return super.takeScreenshot(namePrefix); }
}
