package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomerSupportTests extends BaseTest {

    @Test(description = "Access and open customer support form")
    public void testAccessSupportForm() {
        ExtentTest test = extent.createTest("Customer Support - Access Form", "Verify support form opens correctly");
        try {
            driver.get(ConfigReader.get("baseurl"));
            test.info("Navigated to base URL");
            pages.getCustomerSupportPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getCustomerSupportPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getCustomerSupportPage().clickLogin(); test.info("Clicked Login");
            test.info("Login successful");
            pages.getCustomerSupportPage().clickContactUs();
            String screenshot = pages.getCustomerSupportPage().takeScreenshot("SupportForm_Access");
            test.pass("Customer support form opened successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Failed to access customer support form: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Submit form with valid user information")
    public void testSubmitSupportFormValid() {
        ExtentTest test = extent.createTest("Customer Support - Valid Submission", "Verify support form submits with valid details");
        try {
            driver.get(ConfigReader.get("baseurl"));
            test.info("Navigated to base URL");
            pages.getCustomerSupportPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getCustomerSupportPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getCustomerSupportPage().clickLogin(); test.info("Clicked Login");
            test.info("Login successful");
            pages.getCustomerSupportPage().clickContactUs();
            pages.getCustomerSupportPage().enterName("John Doe");
            pages.getCustomerSupportPage().enterEmail("john.doe@test.com");
            pages.getCustomerSupportPage().enterPhone("9876554321");
            pages.getCustomerSupportPage().enterMessage("I need help with my account.");
            pages.getCustomerSupportPage().clickSubmit();

            Assert.assertTrue(pages.getCustomerSupportPage().getSuccessMessage().contains("Customer Care"));
            String screenshot = pages.getCustomerSupportPage().takeScreenshot("SupportForm_Valid");
            test.pass("Support form submitted successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Support form valid submission failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Submit form with empty fields")
    public void testSubmitSupportFormEmptyFields() {
        ExtentTest test = extent.createTest("Customer Support - Empty Fields", "Verify validation when form fields are empty");
        try {
            driver.get(ConfigReader.get("baseurl"));
            test.info("Navigated to base URL");
            pages.getCustomerSupportPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getCustomerSupportPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getCustomerSupportPage().clickLogin(); test.info("Clicked Login");
            test.info("Login successful");
            pages.getCustomerSupportPage().clickContactUs();
            pages.getCustomerSupportPage().enterName("");
            pages.getCustomerSupportPage().enterEmail("");
            pages.getCustomerSupportPage().enterPhone("");
            pages.getCustomerSupportPage().enterMessage("");
            pages.getCustomerSupportPage().clickSubmit();

            Assert.assertTrue(pages.getCustomerSupportPage().getErrorMessage().contains("Required fields"));
            String screenshot = pages.getCustomerSupportPage().takeScreenshot("SupportForm_Empty");
            test.pass("Validation error displayed for empty fields", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Support form empty fields validation failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify form submission success message")
    public void testSupportFormSuccessMessage() {
        ExtentTest test = extent.createTest("Customer Support - Success Message", "Verify success message is displayed after submission");
        try {
            driver.get(ConfigReader.get("baseurl"));
            test.info("Navigated to base URL");
            pages.getCustomerSupportPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getCustomerSupportPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getCustomerSupportPage().clickLogin(); test.info("Clicked Login");
            test.info("Login successful");
            pages.getCustomerSupportPage().clickContactUs();
            pages.getCustomerSupportPage().enterName("Jane Smith");
            pages.getCustomerSupportPage().enterEmail("jane.smith@test.com");
            pages.getCustomerSupportPage().enterPhone("9876554321");
            pages.getCustomerSupportPage().enterMessage("Need help with fund transfer.");
            pages.getCustomerSupportPage().clickSubmit();

            String successMsg = pages.getCustomerSupportPage().getSuccessMessage();
            Assert.assertTrue(successMsg.contains("Customer Care"), "Expected success message shown");
            String screenshot = pages.getCustomerSupportPage().takeScreenshot("SupportForm_SuccessMsg");
            test.pass("Support form success message validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Support form success message validation failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
