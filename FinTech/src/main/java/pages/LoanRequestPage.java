package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoanRequestPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By loanAmountField = By.id("amount");
    private By loanTermField = By.id("downPayment");
    private By purposeField = By.id("loanPurpose");
    private By submitBtn = By.xpath("//input[@value='Apply Now']");
    private By successMessage = By.xpath("//h1[text()='Loan Request Processed']");
    private By errorMessage = By.xpath("//*[normalize-space(text())='An internal error has occurred and has been logged.']");
    private By statusMessage = By.id("loanStatus");
    private By userName = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//*[@type='submit']");
    private By accountSummaryHeader = By.xpath("//h1[normalize-space()='Accounts Overview']");
//    public LoanRequestPage(WebDriver driver) {
//        super(driver);
//        this.driver = driver;
//    }

    public LoanRequestPage(WebDriver driver) { super(); }
    public void enterUserName(String userInput) { sendKeys(userName, userInput); }
    public void enterPassword(String pwd) { sendKeys(password, pwd); }
    public void clickLogin() { click(loginBtn); }
    // Actions
    public boolean isAccountSummaryVisible() { return isDisplayed(accountSummaryHeader); }
    public void enterLoanAmount(String amount) { sendKeys(loanAmountField, amount); }
    public void enterLoanTerm(String term) { sendKeys(loanTermField, term); }
    public void enterPurpose(String purpose) { sendKeys(purposeField, purpose); }
    public void clickSubmit() { click(submitBtn); }

    public String getSuccessMessage() { return getText(successMessage); }
    public String getErrorMessage() { return getText(errorMessage); }
    public String getLoanStatus() { return getText(statusMessage); }

    public String takeScreenshot(String namePrefix) { return super.takeScreenshot(namePrefix); }
}
