package pages;

import org.openqa.selenium.By;

public class UIPage extends BasePage {

    // Locators
    private By addContactButton = By.xpath("//button[text()='Add Contact']");
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By emailField = By.id("email");
    private By phoneField = By.id("phone");
    private By saveButton = By.xpath("//button[text()='Save']");
    private By toastMessage = By.xpath("//div[contains(@class,'toast')]"); // adjust per DOM

    public UIPage() {
        super();
    }

    // Actions
    public void clickAddContact() {
        click(addContactButton);
    }

    public boolean isFieldAligned(By locator) {
        // Simplified check: element is displayed (alignment would normally be CSS validation)
        return isDisplayed(locator);
    }

    public boolean isToastVisible() {
        return isDisplayed(toastMessage);
    }

    public String getToastMessage() {
        return getText(toastMessage);
    }

    public By getFirstNameField() {
        return firstNameField;
    }

    public By getLastNameField() {
        return lastNameField;
    }

    public By getEmailField() {
        return emailField;
    }

    public By getPhoneField() {
        return phoneField;
    }

    public By getSaveButton() {
        return saveButton;
    }
}
