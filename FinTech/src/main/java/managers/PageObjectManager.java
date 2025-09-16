package managers;

import org.openqa.selenium.WebDriver;
import pages.*;
//import pages.DeleteContactPage;

public class PageObjectManager {

    private WebDriver driver;

    private LoginPage loginPage;
    private AccountPage accountPage;
    private FundsTransferPage fundsTransferPage;
    private LoanRequestPage loanRequestPage;
    private CustomerSupportPage customerSupportPage;
    private NavigationUIPage navigationUIPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
    }

    public AccountPage getAccountPage() {
        return (accountPage == null) ? accountPage = new AccountPage(driver) : accountPage;
    }

    public FundsTransferPage getFundsTransferPage() {
        return (fundsTransferPage == null) ? fundsTransferPage = new FundsTransferPage(driver) : fundsTransferPage;
    }

    public NavigationUIPage getNavigationUIPage() {
        return (navigationUIPage == null) ? navigationUIPage = new NavigationUIPage(driver) : navigationUIPage;
    }
    public LoanRequestPage getLoanRequestPage() {
        return (loanRequestPage == null) ? loanRequestPage = new LoanRequestPage(driver) : loanRequestPage;
    }
    public CustomerSupportPage getCustomerSupportPage() {
        return (customerSupportPage == null) ? customerSupportPage = new CustomerSupportPage(driver) : customerSupportPage;
    }
    
}
