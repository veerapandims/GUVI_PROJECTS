package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactListPage;
import java.io.IOException;

@Slf4j
@Listeners({listeners.TestListener.class})
public class LoginTests extends BaseTest {
    String url = ConfigReader.get("baseurl");
    String email = ConfigReader.get("email");
    String pwd = ConfigReader.get("password");
    String in_email = ConfigReader.get("invalidEmail");

    @Test(description = "Verify login with valid credentials",priority = 0)
    public void validLogin() throws IOException {
        ExtentTest test = extent.createTest("Valid Login Test", "Verify login with valid credentials");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            pages.getLoginPage().waitForVisiblity();test.info("Login Success");
            ContactListPage cl = pages.getContactListPage();
            Assert.assertTrue(cl.isContactPresent("Contacts") || cl.isContactPresent("contact"),"Contact list not visible");
            String screenshotPath = pages.getLoginPage().takeScreenshot("Scenario verified Successfully");
            test.pass("Scenario verified Successfully",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getLoginPage().takeScreenshot("login Failed");
            test.fail("Login failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Login failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify login with invalid password",priority = 1)
    public void login_with_invalid_password() throws IOException {
        ExtentTest test = extent.createTest("invalid Login Test", "Verify login with invalid password");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail("test@example.com");test.info("Entered Email");
            pages.getLoginPage().enterPassword("WrongPass");test.info("Entered Password");
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            pages.getLoginPage().waitForVisiblityError();
            Assert.assertTrue(pages.getLoginPage().isLoginButtonPresent(),
                    "Login button still present (server handled error)");test.info("Assertion successful");
            String screenshotPath = pages.getLoginPage().takeScreenshot("Scenario verified Successfully");
            test.pass("Scenario verified Successfully",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getLoginPage().takeScreenshot("login Failed");
            test.fail("Login failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Login failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    @Test(description = "Verify login with empty fields",priority = 2)
    public void login_with_empty_fields() throws IOException {
        ExtentTest test = extent.createTest("empty Login Test", "Verify login with empty fields");
        try {
            driver.get(url);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            pages.getLoginPage().waitForVisiblityError();
            String errorMsg = pages.getLoginPage().getLoginMessageForEmail();
            Assert.assertTrue(errorMsg.contains("Incorrect username or password") ||
                            errorMsg.contains("error"),
                    "Validation message shown for empty fields");test.info("Assertion successful");
            String screenshotPath = pages.getLoginPage().takeScreenshot("Scenario verified Successfully");
            test.pass("Scenario verified Successfully",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getLoginPage().takeScreenshot("login Failed");
            test.fail("Login failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Login failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    @Test(description = "Verify login with invalid email format",priority = 3)
    public void login_With_Invalid_Email_Format() throws IOException {
        ExtentTest test = extent.createTest("invalid Login email Test", "Verify login with invalid email format");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(in_email);test.info("Entered Email :: "+ in_email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            String errorMsg = pages.getLoginPage().getLoginMessageForEmail();
            Assert.assertTrue(errorMsg.contains("Incorrect username or password") ||
                            errorMsg.contains("error"),
                    "Validation message shown for empty fields");test.info("Assertion successful");
            String screenshotPath = pages.getLoginPage().takeScreenshot("Scenario verified Successfully");
            test.pass("Scenario verified Successfully",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getLoginPage().takeScreenshot("login Failed");
            test.fail("Login failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Login failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify password field masks input",priority = 4)
    public void verify_Password_Masking() throws IOException {
        ExtentTest test = extent.createTest("Verify password mask Test", "Verify password field masks input");
        try {
            driver.get(url);
            String typeAttr = pages.getLoginPage().getPasswordFieldType();
            Assert.assertEquals(typeAttr, "password", "Password field is masked!");test.info("Password field is masked");
            String screenshotPath = pages.getLoginPage().takeScreenshot("Scenario verified Successfully");
            test.pass("Scenario verified Successfully",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getLoginPage().takeScreenshot("login Failed");
            test.fail("Login failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Login failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
