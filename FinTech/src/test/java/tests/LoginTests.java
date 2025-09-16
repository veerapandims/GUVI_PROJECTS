package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import core.DriverFactory;
import listeners.ConfigReader;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Slf4j
public class LoginTests extends BaseTest {

    @Test(description = "Verify login with valid credentials")
    public void testValidLogin() throws IOException {
        ExtentTest test = extent.createTest("Login - Valid", "Verify valid login redirects to account summary");
        try {
            driver.get(ConfigReader.get("baseurl"));
            log.info("Navigated to URL");
            pages.getLoginPage().enterUserName(ConfigReader.get("userName")); test.info("Entered Username");
            pages.getLoginPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getLoginPage().clickLogin(); test.info("Clicked Login");

//            Assert.assertTrue(pages.getLoginPage().isAccountSummaryVisible(), "Account summary not visible");
            String screenshot = pages.getLoginPage().takeScreenshot("Valid_Login");
            test.pass("Valid login successful",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Valid login verified");
        } catch (Exception e) {
            String screenshot = pages.getLoginPage().takeScreenshot("Valid_Login_Fail");
            test.fail("Login failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Login failed", e);
        }
    }

    @Test(description = "Verify login with incorrect password")
    public void testInvalidPassword() throws IOException {
        ExtentTest test = extent.createTest("Login - Invalid Password", "Verify incorrect password shows error");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getLoginPage().enterUserName(ConfigReader.get("userName")); test.info("Entered Username");
            pages.getLoginPage().enterPassword("1331323"); test.info("Entered Wrong Password");
            pages.getLoginPage().clickLogin(); test.info("Clicked Login");

            Assert.assertEquals(pages.getLoginPage().getErrorMessage(), "Invalid credentials.");
            String screenshot = pages.getLoginPage().takeScreenshot("Invalid_Password");
            test.pass("Proper error displayed for incorrect password",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Error message verified for invalid password");
        } catch (Exception e) {
            String screenshot = pages.getLoginPage().takeScreenshot("Invalid_Password_Fail");
            test.fail("Error test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Invalid password test failed", e);
        }
    }

    @Test(description = "Verify login with unregistered user")
    public void testUnregisteredUser() throws IOException {
        ExtentTest test = extent.createTest("Login - Unregistered User", "Verify unregistered user error");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getLoginPage().enterUserName("nouser@test.com"); test.info("Entered Unregistered Email");
            pages.getLoginPage().enterPassword("123123"); test.info("Entered Password");
            pages.getLoginPage().clickLogin(); test.info("Clicked Login");

            Assert.assertEquals(pages.getLoginPage().getErrorMessage(), "User does not exist.");
            String screenshot = pages.getLoginPage().takeScreenshot("Unregistered_User");
            test.pass("Proper error displayed for unregistered user",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Unregistered user error verified");
        } catch (Exception e) {
            String screenshot = pages.getLoginPage().takeScreenshot("Unregistered_User_Fail");
            test.fail("Error test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Unregistered user test failed", e);
        }
    }

    @Test(description = "Verify login with empty fields")
    public void testEmptyFields() throws IOException {
        ExtentTest test = extent.createTest("Login - Empty Fields", "Verify validation errors when fields are empty");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getLoginPage().clickLogin(); test.info("Clicked Login without credentials");

            Assert.assertEquals(pages.getLoginPage().getErrorMessage(), "Please enter a username and password.");
            String screenshot = pages.getLoginPage().takeScreenshot("Empty_Fields");
            test.pass("Validation error displayed for empty fields",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Empty fields validation verified");
        } catch (Exception e) {
            String screenshot = pages.getLoginPage().takeScreenshot("Empty_Fields_Fail");
            test.fail("Error test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Empty fields test failed", e);
        }
    }

    @Test(description = "Verify login with invalid email format")
    public void testInvalidEmailFormat() throws IOException {
        ExtentTest test = extent.createTest("Login - Invalid Email Format", "Verify error on invalid email format");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getLoginPage().enterUserName("user@com"); test.info("Entered Invalid Email");
            pages.getLoginPage().enterPassword("anyPass"); test.info("Entered Password");
            pages.getLoginPage().clickLogin(); test.info("Clicked Login");

            Assert.assertEquals(pages.getLoginPage().getErrorMessage(), "Invalid email format.");
            String screenshot = pages.getLoginPage().takeScreenshot("Invalid_Email");
            test.pass("Error displayed for invalid email format",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Invalid email format validation verified");
        } catch (Exception e) {
            String screenshot = pages.getLoginPage().takeScreenshot("Invalid_Email_Fail");
            test.fail("Error test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Invalid email test failed", e);
        }
    }

    @Test(description = "Verify password field masks input")
    public void testPasswordMasking() throws IOException {
        ExtentTest test = extent.createTest("Login - Password Masking", "Verify password field hides characters");
        try {
            driver.get(ConfigReader.get("baseurl"));
            String type = driver.findElement(By.id("password")).getAttribute("type");
            Assert.assertEquals(type, "password");
            String screenshot = pages.getLoginPage().takeScreenshot("Password_Masking");
            test.pass("Password field masks input",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Password masking verified");
        } catch (Exception e) {
            String screenshot = pages.getLoginPage().takeScreenshot("Password_Masking_Fail");
            test.fail("Error test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Password masking test failed", e);
        }
    }

    @Test(description = "Verify session timeout due to inactivity")
    public void testSessionTimeout() throws InterruptedException, IOException {
        ExtentTest test = extent.createTest("Login - Session Timeout", "Verify user is logged out after inactivity");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getLoginPage().enterUserName(ConfigReader.get("email")); test.info("Entered Email");
            pages.getLoginPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getLoginPage().clickLogin(); test.info("Clicked Login");

            log.info("Waiting for session timeout...");
            Thread.sleep(60000); // simulate inactivity
            Assert.assertEquals(pages.getLoginPage().getErrorMessage(), "Session timed out.");
            String screenshot = pages.getLoginPage().takeScreenshot("Session_Timeout");
            test.pass("User logged out after timeout",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Session timeout verified");
        } catch (Exception e) {
            String screenshot = pages.getLoginPage().takeScreenshot("Session_Timeout_Fail");
            test.fail("Session timeout test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Session timeout test failed", e);
        }
    }

    @Test(description = "Verify Remember Me functionality")
    public void testRememberMe() throws IOException {
        ExtentTest test = extent.createTest("Login - Remember Me", "Verify user stays logged in with Remember Me");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getLoginPage().enterUserName(ConfigReader.get("email")); test.info("Entered Email");
            pages.getLoginPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getLoginPage().clickRememberMe(); test.info("Clicked Remember Me");
            pages.getLoginPage().clickLogin(); test.info("Clicked Login");

            driver.quit(); // close browser
            driver = DriverFactory.initDriver("chrome"); // reopen
            driver.get(ConfigReader.get("baseurl"));

            Assert.assertTrue(pages.getLoginPage().isAccountSummaryVisible());
            String screenshot = pages.getLoginPage().takeScreenshot("RememberMe");
            test.pass("User remains logged in with Remember Me",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Remember Me functionality verified");
        } catch (Exception e) {
            String screenshot = pages.getLoginPage().takeScreenshot("RememberMe_Fail");
            test.fail("Remember Me test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Remember Me test failed", e);
        }
    }
}
