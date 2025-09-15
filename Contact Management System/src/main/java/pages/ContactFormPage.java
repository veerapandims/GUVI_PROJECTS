package pages;

import org.openqa.selenium.By;

import static org.apache.commons.io.file.FilesUncheck.getAttribute;

public class ContactFormPage extends BasePage {

    private By firstName = By.id("firstName");
    private By address = By.id("address");
    private By saveBtn = By.xpath("//button[text()='Save']");
    private By errorMessage = By.cssSelector(".error-message");
    private By contactCard = By.cssSelector(".contact-item");

    public ContactFormPage() {
        super();
    }

    // Actions
    public void enterFirstName(String name) {
        sendKeys(firstName, name);
    }

    public void enterAddress(String addr) {
        sendKeys(address, addr);
    }

    public void clickSave() {
        click(saveBtn);
    }

    // Validations
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isContactAdded() {
        return isDisplayed(contactCard);
    }

    @Override
    public String takeScreenshot(String namePrefix) {
        return super.takeScreenshot(namePrefix);
    }
}
