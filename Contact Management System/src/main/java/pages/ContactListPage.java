package pages;

import org.openqa.selenium.By;

public class ContactListPage extends BasePage {
    private By addContactBtn = By.xpath("//button[@id='add-contact']");
    private By logoutBtn = By.xpath("//a[text()='Logout']");
    private By contactRows = By.cssSelector(".contact-row");

    public ContactListPage() { super(); }

    public void clickAddContact() { click(addContactBtn); }
    public void clickLogout() { click(logoutBtn); }
    public boolean isContactPresent(String name) {
        return driver.getPageSource().contains(name);
    }
}
