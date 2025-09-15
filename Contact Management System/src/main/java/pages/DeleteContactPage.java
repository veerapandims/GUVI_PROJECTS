package pages;

import org.openqa.selenium.By;

public class DeleteContactPage extends BasePage {

    // Locators
    private By deleteIcon = By.id("delete");   // Update if your DOM differs
    private By confirmDelete = By.id("delete");
    private By cancelDelete = By.xpath("//button[text()='Cancel']");
    private By contactListHeader = By.xpath("//h1[text()='Contact Details']");
    private By errorMessage = By.xpath("//div[contains(@class,'error')]");
    private By email = By.id("email");
    private By password = By.id("password");
    private By loginBtn = By.xpath("//button[text()='Submit']");
    private By clickUser = By.xpath("(//td[text()='sachin dendulkar'])[1]");
//    public DeleteContactPage() {
//        super();
//    }

    public void enterEmail(String e) { sendKeys(email, e); }
    public void enterPassword(String p) { sendKeys(password, p); }
    public void clickLogin() { click(loginBtn); }

    public void clickOnUser() {
        click(clickUser);
    }

    // Actions
    public void clickDeleteIcon() {
        click(deleteIcon);
    }

    public void confirmDelete() {
//        click(confirmDelete);
    }

    public void cancelDelete() {
//        click(cancelDelete);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public void waitForContactListVisible() {
        waitForVisibility(contactListHeader);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public boolean isContactPresent(String contactName) {
        return driver.getPageSource().contains(contactName);
    }
}
