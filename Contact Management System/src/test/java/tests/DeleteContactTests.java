package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactListPage;
import pages.DeleteContactPage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DeleteContactTests extends BaseTest {
    String url = ConfigReader.get("baseurl");
    String email = ConfigReader.get("email");
    String pwd = ConfigReader.get("password");
    @Test(description = "Verify deleting a contact")
    public void verifyDeleteContact() throws IOException {
        ExtentTest test = extent.createTest("Verify deleting a contact",
                "Contact should be removed from list after deletion");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            ContactListPage cl = pages.getContactListPage();
            DeleteContactPage del = pages.getDeleteContactPage();
            String contactName = "ANUP KUMAR"; // example contact
            Assert.assertTrue(del.isContactPresent(contactName), "Contact should be present before deletion");
            test.info("Contact exists before deletion :: " + contactName);
            del.clickOnUser();
            del.clickDeleteIcon();
            test.info("Clicked delete icon for contact :: " + contactName);
            Alert alert = driver.switchTo().alert();
            alert.accept();
            del.confirmDelete();
            test.info("Confirmed deletion");
            del.waitForContactListVisible();
            Assert.assertFalse(del.isContactPresent(contactName), "Contact should be removed after deletion");
            test.pass("Contact deleted successfully",
                    MediaEntityBuilder.createScreenCaptureFromPath(del.takeScreenshot("Delete_Contact_Success")).build());

        } catch (Exception e) {
            String screenshotPath = pages.getDeleteContactPage().takeScreenshot("Delete_Contact_Failure");
            test.fail("Delete contact test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Delete contact test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify delete confirmation appears before deleting")
    public void verifyDeleteConfirmation() throws IOException {
        ExtentTest test = extent.createTest("Verify delete confirmation",
                "Confirmation alert should appear before deletion");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            ContactListPage cl = pages.getContactListPage();
            DeleteContactPage del = pages.getDeleteContactPage();
            String contactName = "ANUP KUMAR"; // example contact
            Assert.assertTrue(del.isContactPresent(contactName), "Contact should be present before deletion");
            test.info("Contact exists before deletion :: " + contactName);
            del.clickOnUser();
            del.clickDeleteIcon();
            test.info("Clicked delete icon for contact :: " + contactName);
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            del.confirmDelete();
            test.info("Cancelled delete");
            del.waitForContactListVisible();
        } catch (Exception e) {
            String screenshotPath = pages.getDeleteContactPage().takeScreenshot("Delete_Confirmation_Failure");
            test.fail("Delete confirmation test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Delete confirmation test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify deleted contact does not reappear after refresh")
    public void verifyDeletedContactNotPresentAfterRefresh() throws IOException {
        ExtentTest test = extent.createTest("Verify deleted contact does not reappear after refresh",
                "Deleted contact should remain removed after page refresh");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            ContactListPage cl = pages.getContactListPage();
            DeleteContactPage del = pages.getDeleteContactPage();
            String contactName = "ANUP KUMAR"; // example contact
            Assert.assertTrue(del.isContactPresent(contactName), "Contact should be present before deletion");
            test.info("Contact exists before deletion :: " + contactName);
            del.clickOnUser();
            del.clickDeleteIcon();
            test.info("Clicked delete icon for contact :: " + contactName);
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            del.confirmDelete();
            del.refreshPage();
            test.info("Refreshed page");
            del.waitForContactListVisible();
            Assert.assertFalse(del.isContactPresent(contactName),
                    "Deleted contact should not reappear after refreshing the page");
            test.pass("Deleted contact did not reappear after refresh",
                    MediaEntityBuilder.createScreenCaptureFromPath(del.takeScreenshot("Delete_Contact_Refresh")).build());

        } catch (Exception e) {
            String screenshotPath = pages.getDeleteContactPage().takeScreenshot("Delete_Contact_Refresh_Failure");
            test.fail("Deleted contact refresh test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Deleted contact refresh test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
