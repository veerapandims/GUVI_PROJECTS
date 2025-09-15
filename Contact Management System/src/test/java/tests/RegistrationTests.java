package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistrationTests extends BaseTest {

    private String url = ConfigReader.get("baseurl");
    private String email = ConfigReader.get("email");
    private String existEmail = ConfigReader.get("existEmail");
    private String pwd = ConfigReader.get("newpwd");
    private String in_email = ConfigReader.get("invalidEmail");
    private String fName = ConfigReader.get("firstName");
    private String lName = ConfigReader.get("lastName");

    @Test(description = "Verify sign-up with valid inputs")
    public void testValidSignUp() {
        ExtentTest test = extent.createTest("Sign Up - Valid Inputs",
                "Verify sign-up with valid email and password");
        try {
            driver.get(url);
            test.info("Launch URL :: " + url);
            RegistrationPage reg = pages.getRegistrationPage();
            reg.clickSignup();
            test.info("Clicked Sign Up");
            reg.enterfName(fName);
            test.info("Entered First Name :: " + fName);
            reg.enterlName(lName);
            test.info("Entered Last Name :: " + lName);
            reg.enterEmail(email);
            test.info("Entered Email :: " + email);
            reg.enterPassword(pwd);
            test.info("Entered Password :: " + pwd);
            reg.clickSignUp();
            test.info("Clicked Sign Up");
            String success = reg.getSuccessMessage();
            Assert.assertTrue(success.contains("Registration successful"), "Expected success message found.");
            test.pass("Registration successful",
                    MediaEntityBuilder.createScreenCaptureFromPath(reg.takeScreenshot("SignUp_Valid")).build());
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            log.error("Test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify sign-up with existing email")
    public void testSignUpWithExistingEmail() {
        ExtentTest test = extent.createTest("Sign Up - Existing Email",
                "Verify registration fails with existing email");
        try {
            driver.get(url);
            test.info("Launch URL :: " + url);
            RegistrationPage reg = pages.getRegistrationPage();
            reg.clickSignup();
            test.info("Clicked Sign Up");
            reg.enterfName(fName);
            test.info("Entered First Name :: " + fName);
            reg.enterlName(lName);
            test.info("Entered Last Name :: " + lName);
            reg.enterEmail(existEmail);
            test.info("Entered Existing Email :: " + existEmail);
            reg.enterPassword(pwd);
            test.info("Entered Password :: " + pwd);
            test.info("Validated error message");
            reg.getExistsErrorMessage();
            reg.clickSignUp();
            test.info("Clicked Sign Up");
            String error = reg.getExistsErrorMessage();
            Assert.assertTrue(error.contains("Email address is already in use"), "Expected 'email exists' error not found.");
            test.pass("Proper error shown for existing email",
                    MediaEntityBuilder.createScreenCaptureFromPath(reg.takeScreenshot("SignUp_ExistingEmail")).build());
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            log.error("Test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify sign-up with blank fields")
    public void testSignUpWithBlankFields() {
        ExtentTest test = extent.createTest("Sign Up - Blank Fields",
                "Verify registration fails with empty fields");
        try {
            driver.get(url);
            test.info("Launch URL :: " + url);

            RegistrationPage reg = pages.getRegistrationPage();
            reg.clickSignup();
            test.info("Clicked Sign Up");
            reg.enterfName("");
            test.info("Left First Name blank");
            reg.enterlName("");
            test.info("Left Last Name blank");
            reg.enterEmail("");
            test.info("Left Email blank");
            reg.enterPassword("");
            test.info("Left Password blank");
            reg.clickSignUp();
            test.info("Clicked Sign Up");
            String error = reg.getRequiredFieldMessage();
            test.info("Validated Error Message");
            Assert.assertTrue(error.contains("User validation failed"), "Expected required field message not shown.");
            test.pass("Proper error shown for blank fields",
                    MediaEntityBuilder.createScreenCaptureFromPath(reg.takeScreenshot("SignUp_BlankFields")).build());
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            log.error("Test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
