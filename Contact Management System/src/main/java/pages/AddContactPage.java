package pages;

import org.openqa.selenium.By;

public class AddContactPage extends BasePage {
    private By firstName = By.id("firstName");
    private By lastName = By.id("lastName");
    private By email = By.id("email");
    private By phone = By.id("phone");
    private By dob = By.id("birthdate");
    private By pAddress = By.id("street1");
    private By sAddress = By.id("street2");
    private By city = By.id("city");
    private By state = By.id("stateProvince");
    private By postal = By.id("postalCode");
    private By country = By.id("country");
    private By submit = By.id("submit");

    public AddContactPage() { super(); }

    public void enterFirstName(String userFirstName) { sendKeys(firstName, userFirstName); }
    public void enterLastName(String userLastName) { sendKeys(lastName, userLastName); }
    public void enterDOB(String userDOB) { sendKeys(dob, userDOB); }
    public void enterEmail(String userEmail) { sendKeys(email, userEmail); }
    public void enterPhone(String userPhone) { sendKeys(phone, userPhone); }
    public void enterFirstAddress(String userAddress) { sendKeys(pAddress, userAddress); }
    public void enterSecondAddress(String secondAddress) { sendKeys(sAddress, secondAddress); }
    public void enterCity(String userCity) { sendKeys(city, userCity); }
    public void enterState(String userState) { sendKeys(state, userState); }
    public void enterPostal(String userPostal) { sendKeys(postal, userPostal); }
    public void enterCountry(String userCountry) { sendKeys(country, userCountry); }
    public void submitForm() { click(submit); }

}
