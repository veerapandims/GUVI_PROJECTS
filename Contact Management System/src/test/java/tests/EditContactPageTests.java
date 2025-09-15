package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EditContactPage;

import java.io.IOException;

@Slf4j
public class EditContactPageTests extends BaseTest {
    String email = ConfigReader.get("email");
    String pwd = ConfigReader.get("password");
    @Test(description = "Verify user can edit an existing contact")
    public void testEditContact() throws IOException {
        ExtentTest test = extent.createTest("Edit Contact Test", "Verify user can edit an existing contact");
        try {
            driver.get(ConfigReader.get("baseurl"));
            test.info("Navigated to URL :: " + ConfigReader.get("baseurl"));
            pages.getEditContactPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getEditContactPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getEditContactPage().clickLogin();test.info("Clicked Login");
            EditContactPage ed = pages.getEditContactPage();
            ed.clickEdit();
            test.info("Clicked Edit");
            ed.updatePhone("124578965");
            test.info("Updated Phone Number");
            ed.clickSave();
            test.info("Clicked Save");
            Assert.assertTrue(driver.getPageSource().contains("updated Mobile Number"),
                    "Contact Mobile updated!");
            String screenshotPath = pages.getEditContactPage().takeScreenshot("Edit_Contact_Success");
            test.pass("Contact edited successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getEditContactPage().takeScreenshot("Edit_Contact_Failed");
            test.fail("Failed to edit contact: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Failed to edit contact: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify canceling an edit")
    public void testCancelEdit() throws IOException {
        ExtentTest test = extent.createTest("Cancel Edit Contact Test", "Verify canceling an edit does not save changes");
        try {
            driver.get(ConfigReader.get("baseurl"));
            test.info("Navigated to URL :: " + ConfigReader.get("baseurl"));
            pages.getEditContactPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getEditContactPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getEditContactPage().clickLogin();test.info("Clicked Login");
            EditContactPage ed = pages.getEditContactPage();
            ed.clickEdit();
            test.info("Clicked Edit");
            ed.updatePhone("124578965");
            test.info("Updated Phone Number");
            ed.clickCancel();
            test.info("Clicked Cancel");
            Assert.assertFalse(driver.getPageSource().contains("temp_email@test.com"),
                    "Changes should not be saved when cancelled!");
            String screenshotPath = pages.getEditContactPage().takeScreenshot("Cancel_Edit_Success");
            test.pass("Cancel edit verified successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getEditContactPage().takeScreenshot("Cancel_Edit_Failed");
            test.fail("Failed cancel edit: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Failed cancel edit: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify validation during edit")
    public void testValidationOnEdit() throws IOException {
        ExtentTest test = extent.createTest("Edit Contact Validation Test", "Verify validation errors during edit");
        try {
            driver.get(ConfigReader.get("baseurl"));
            test.info("Navigated to URL :: " + ConfigReader.get("baseurl"));
            pages.getEditContactPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getEditContactPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getEditContactPage().clickLogin();test.info("Clicked Login");
            EditContactPage ed = pages.getEditContactPage();
            ed.clickEdit();
            test.info("Clicked Edit");
            ed.clearLastName();
            test.info("Cleared Last Name");
            ed.clickSave();
            test.info("Clicked Save");
            String error = pages.getEditContactPage().getErrorMessage();
            Assert.assertEquals(error, "Validation failed: lastName: Path `lastName` is required.",
                    "Expected validation error not shown!");
            String screenshotPath = pages.getEditContactPage().takeScreenshot("Validation_Edit_Success");
            test.pass("Validation error displayed successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getEditContactPage().takeScreenshot("Validation_Edit_Failed");
            test.fail("Validation check failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Validation check failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
