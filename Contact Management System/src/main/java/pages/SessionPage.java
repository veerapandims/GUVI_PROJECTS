package pages;

import org.openqa.selenium.By;

public class SessionPage extends BasePage {

    // Locators
    private By logoutButton = By.xpath("//button[text()='Logout']");
    private By loginHeader = By.xpath("//p[text()='Log In:']");
    private By contactListHeader = By.xpath("//h1[text()='Contact List']");
    private By email = By.id("email");
    private By password = By.id("password");
    private By loginBtn = By.xpath("//button[text()='Submit']");

    public SessionPage() {
        super();
    }
    public void enterEmail(String e) { sendKeys(email, e); }
    public void enterPassword(String p) { sendKeys(password, p); }
    public void clickLogin() { click(loginBtn); }

    // Actions
    public void clickLogout() {
        click(logoutButton);
    }

    public void waitForLoginPage() {
        waitForVisibility(loginHeader);
    }

    public void waitForContactListPage() {
        waitForVisibility(contactListHeader);
    }

    public boolean isOnLoginPage() {
        return isDisplayed(loginHeader);
    }

    public boolean isOnContactListPage() {
        return isDisplayed(contactListHeader);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateDirectToContactList(String baseUrl) {
        driver.get(baseUrl + "/contact-list");
    }

    public void navigateBack() {
        driver.navigate().back();
    }
}
