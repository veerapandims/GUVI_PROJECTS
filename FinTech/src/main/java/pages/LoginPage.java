package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

    public class LoginPage extends BasePage {

        private WebDriver driver;

        // Locators
        private By userName = By.name("username");
        private By password = By.name("password");
        private By loginBtn = By.xpath("//*[@type='submit']");
        private By rememberMe = By.id("remember");
        private By errorMessage = By.xpath("//p[text()='Please enter a username and password.']");
        private By accountSummaryHeader = By.xpath("//h1[normalize-space()='Account Summary']");

//        public LoginPage(WebDriver driver) {
//            super(driver);
//            this.driver = driver;
//        }
        public LoginPage(WebDriver driver) { super(); }

        public void enterUserName(String userInput) { sendKeys(userName, userInput); }
        public void enterPassword(String pwd) { sendKeys(password, pwd); }
        public void clickLogin() { click(loginBtn); }
        public void clickRememberMe() { click(rememberMe); }

        public String getErrorMessage() { return getText(errorMessage); }
        public boolean isAccountSummaryVisible() { return isDisplayed(accountSummaryHeader); }
    }





//    private By email = By.name("username");
//    private By password = By.name("password");
//    private By loginBtn = By.xpath("//input[@type='submit']");
//    private By signupLink = By.xpath("//button[text()='Sign up']");
//    private By loginError = By.xpath("//span[text()='Incorrect username or password']");
//    private By contactListHeader = By.xpath("//*[normalize-space(.) = 'Available Amount']");
//
//    public LoginPage() { super(); }
//
//    public void enterEmail(String e) { sendKeys(email, e); }
//    public void enterPassword(String p) { sendKeys(password, p); }
//    public void clickLogin() { click(loginBtn); }
//    public void clickSignUp() { click(signupLink); }
//
//    public boolean isLoginButtonPresent() { return isDisplayed(loginError); }
//
//    public String getValidationMessageForEmail() {
//        WebElement emailField = driver.findElement(loginError);
//        return emailField.getAttribute("id");
//    }
//    public String getLoginMessageForEmail() {
//        WebElement emailField = driver.findElement(loginError);
//        return emailField.getText();
//    }
//    public String getPasswordFieldType() {
//        return driver.findElement(password).getAttribute("type");
//    }
//    public void waitForVisiblity(){
//        waitForVisibility(contactListHeader);
//    }
//    public void waitForVisiblityError(){
//        waitForVisibility(loginError);
//    }
//
//    @Override
//    public String takeScreenshot(String namePrefix) {
//        return super.takeScreenshot(namePrefix);
//    }


