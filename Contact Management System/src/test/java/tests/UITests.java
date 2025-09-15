package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.UIPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UITests extends BaseTest {
    String url = ConfigReader.get("baseurl");
    String email = ConfigReader.get("email");
    String pwd = ConfigReader.get("password");
    @Test(description = "Verify alignment of fields on Add Contact form")
    public void verifyFieldAlignment() {
        ExtentTest test = extent.createTest("UI - Field Alignment", "Verify alignment of fields on Add Contact form");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            UIPage ui = pages.getUIPage();
            ui.clickAddContact();
            test.info("Clicked Add Contact button");
            Assert.assertTrue(ui.isFieldAligned(ui.getFirstNameField()), "First Name field should be aligned/displayed");
            test.info("First Name field alignment verified");
            Assert.assertTrue(ui.isFieldAligned(ui.getLastNameField()), "Last Name field should be aligned/displayed");
            test.info("Last Name field alignment verified");
            Assert.assertTrue(ui.isFieldAligned(ui.getEmailField()), "Email field should be aligned/displayed");
            test.info("Email field alignment verified");
            Assert.assertTrue(ui.isFieldAligned(ui.getPhoneField()), "Phone field should be aligned/displayed");
            test.info("Phone field alignment verified");
            Assert.assertTrue(ui.isFieldAligned(ui.getSaveButton()), "Save button should be aligned/displayed");
            test.info("Save button alignment verified");
            test.pass("All fields are properly aligned",
                    MediaEntityBuilder.createScreenCaptureFromPath(ui.takeScreenshot("UI_FieldAlignment")).build());
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            log.error("Field alignment test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify toast message appears after adding a contact")
    public void verifyToastAfterAddContact() {
        ExtentTest test = extent.createTest("UI - Toast After Add Contact",
                "Verify toast message appears after adding a contact");
        try {
            driver.get(ConfigReader.get("URL"));
            test.info("Loaded URL :: " + ConfigReader.get("URL"));
            UIPage ui = pages.getUIPage();
            Assert.assertTrue(ui.isToastVisible(), "Toast message should appear after adding a contact");
            test.info("Toast visibility verified");
            Assert.assertTrue(ui.getToastMessage().length() > 0, "Toast should contain a message");
            test.info("Toast message content verified: " + ui.getToastMessage());
            test.pass("Toast message appears successfully",
                    MediaEntityBuilder.createScreenCaptureFromPath(ui.takeScreenshot("UI_ToastAdd")).build());
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            log.error("Toast after add contact test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify toast message appears after deleting a contact")
    public void verifyToastAfterDeleteContact() {
        ExtentTest test = extent.createTest("UI - Toast After Delete Contact",
                "Verify toast message appears after deleting a contact");
        try {
            driver.get(ConfigReader.get("URL"));
            test.info("Loaded URL :: " + ConfigReader.get("URL"));
            UIPage ui = pages.getUIPage();
            Assert.assertTrue(ui.isToastVisible(), "Toast message should appear after deleting a contact");
            test.info("Toast visibility verified");
            Assert.assertTrue(ui.getToastMessage().length() > 0, "Toast should contain a message");
            test.info("Toast message content verified: " + ui.getToastMessage());
            test.pass("Toast message appears successfully",
                    MediaEntityBuilder.createScreenCaptureFromPath(ui.takeScreenshot("UI_ToastDelete")).build());
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            log.error("Toast after delete contact test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
