package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePage {
    private WebDriver driver;

    private By userName = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//*[@type='submit']");
    // Locators
    private By checkingAccountLink = By.xpath("//h1[normalize-space()='Accounts Overview']");
    private By savingsAccountLink = By.xpath("//a[normalize-space()='Savings Account']");
    private By balanceText = By.xpath("//th[text()='Balance*']");
    private By recentTransactionsTable = By.id("showOverview");
    private By fullTransactionHistoryLink = By.xpath("//a[normalize-space()='Transfer Funds']");
    private By transactionTable = By.id("showOverview");
    private By dateRangeFilter = By.id("transactionDate");
    private By sortByDate = By.id("sort-date");
    private By sortByAmount = By.id("sort-amount");
    private By findTransaction = By.id("findByDate");
    //
//    public AccountPage(WebDriver driver) {
//        super(driver);
//        this.driver = driver;
//    }
    public void enterUserName(String userInput) { sendKeys(userName, userInput); }
    public void enterPassword(String pwd) { sendKeys(password, pwd); }
    public void clickLogin() { click(loginBtn); }
    public AccountPage(WebDriver driver) { super(); }

    // Actions
    public void openCheckingAccount() { click(checkingAccountLink); }
    public void openSavingsAccount() { click(savingsAccountLink); }
    public String getBalance() { return getText(balanceText); }
    public boolean isRecentTransactionsVisible() { return isDisplayed(recentTransactionsTable); }

    public void openTransactionHistory() { click(fullTransactionHistoryLink); }
    public boolean isTransactionHistoryVisible() { return isDisplayed(transactionTable); }

    public boolean validateTransactionDetails(String date, String amount, String type) {
        String rowXpath = String.format("//tr[td[normalize-space()='%s'] and td[normalize-space()='%s'] and td[normalize-space()='%s']]",
                date, amount, type);
        return isDisplayed(By.xpath(rowXpath));
    }

    public void filterByDateRange(String range) { sendKeys(dateRangeFilter, range); }
    public void sortByDate() { click(sortByDate); }
    public void sortByAmount() { click(sortByAmount); }
    public void clickFindTransaction(){
        click(findTransaction);
    }

    public String takeScreenshot(String namePrefix) {
        return super.takeScreenshot(namePrefix);
    }
}
