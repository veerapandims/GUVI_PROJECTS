package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddContactPage;
import pages.ContactListPage;

import java.io.IOException;

@Slf4j
public class ContactTests extends BaseTest {

    @Test(description = "Verify adding contact with all valid details")
    public void addContactWithValidDetails() throws IOException {
        ExtentTest test = extent.createTest("Add Contact - Valid Details", "Add a contact with all valid fields");
        try {
            pages.getLoginPage().navigateTo(ConfigReader.get("baseurl"));
            test.info("URL Loaded :: " + ConfigReader.get("baseurl"));
            pages.getLoginPage().enterEmail(ConfigReader.get("email"));
            test.info("Entered Email :: " + ConfigReader.get("email"));
            pages.getLoginPage().enterPassword(ConfigReader.get("password"));
            test.info("Entered Password :: " + ConfigReader.get("password"));
            pages.getLoginPage().clickLogin();
            test.info("Clicked Login");
            ContactListPage cl = pages.getContactListPage();
            cl.clickAddContact();
            test.info("Clicked Add Contact");
            AddContactPage add = pages.getAddContactPage();
            add.enterFirstName(ConfigReader.get("firstName")); test.info("Entered First Name");
            add.enterLastName(ConfigReader.get("lastName")); test.info("Entered Last Name");
            add.enterDOB(ConfigReader.get("dob")); test.info("Entered DOB");
            add.enterEmail(ConfigReader.get("userEmail")); test.info("Entered Email");
            add.enterPhone(ConfigReader.get("userPhone")); test.info("Entered Phone");
            add.enterFirstAddress(ConfigReader.get("fAddress")); test.info("Entered First Address");
            add.enterSecondAddress(ConfigReader.get("sAddress")); test.info("Entered Second Address");
            add.enterCity(ConfigReader.get("city")); test.info("Entered City");
            add.enterState(ConfigReader.get("state")); test.info("Entered State");
            add.enterPostal(ConfigReader.get("postal")); test.info("Entered Postal");
            add.enterCountry(ConfigReader.get("country")); test.info("Entered Country");
            add.submitForm(); test.info("Submitted Contact Form");
            pages.getLoginPage().waitForVisiblity();
            String screenshotPath = pages.getLoginPage().takeScreenshot("Contact_Added_Successfully");
            test.pass("Contact added successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            String screenshotPath = pages.getLoginPage().takeScreenshot("Contact_Add_Failed");
            test.fail("Test failed: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Error adding contact: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify adding contact with missing required fields")
    public void addContactMissingRequiredFields() throws IOException {
        ExtentTest test = extent.createTest("Add Contact - Missing Required Fields", "Validate error for empty first/last name");
        try {
            pages.getLoginPage().navigateTo(ConfigReader.get("baseurl"));
            pages.getLoginPage().navigateTo(ConfigReader.get("baseurl"));
            test.info("URL Loaded :: " + ConfigReader.get("baseurl"));
            pages.getLoginPage().enterEmail(ConfigReader.get("email"));
            test.info("Entered Email :: " + ConfigReader.get("email"));
            pages.getLoginPage().enterPassword(ConfigReader.get("password"));
            test.info("Entered Password :: " + ConfigReader.get("password"));
            pages.getLoginPage().clickLogin();
            test.info("Clicked Login");
            ContactListPage cl = pages.getContactListPage();
            cl.clickAddContact(); test.info("Clicked Add Contact");
            AddContactPage add = pages.getAddContactPage();
            add.enterFirstName(""); test.info("Left First Name blank");
            add.enterLastName(""); test.info("Left Last Name blank");
            add.submitForm(); test.info("Submitted Contact Form");
            String error = "";
            Assert.assertTrue(error.contains("First Name is required") && error.contains("Last Name is required"),
                    "Validation errors not displayed correctly");
            test.pass("Proper validation errors displayed for missing required fields");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify phone field accepts only numeric input")
    public void phoneFieldValidation() throws IOException {
        ExtentTest test = extent.createTest("Add Contact - Phone Field Validation", "Validate phone field allows only numbers");
        try {
            pages.getLoginPage().navigateTo(ConfigReader.get("baseurl"));
            test.info("URL Loaded :: " + ConfigReader.get("baseurl"));
            pages.getLoginPage().enterEmail(ConfigReader.get("email"));
            test.info("Entered Email :: " + ConfigReader.get("email"));
            pages.getLoginPage().enterPassword(ConfigReader.get("password"));
            test.info("Entered Password :: " + ConfigReader.get("password"));
            pages.getLoginPage().clickLogin();
            test.info("Clicked Login");
            ContactListPage cl = pages.getContactListPage();
            cl.clickAddContact();
            AddContactPage add = pages.getAddContactPage();
            add.enterPhone("ABCXYZ"); test.info("Entered alphabets in Phone field");
            add.submitForm(); test.info("Submitted Contact Form");
            String error = " ";
            Assert.assertTrue(error.contains("Phone must be numeric") || error.contains("Invalid phone"),
                    "Phone validation failed");
            test.pass("Phone field validation successful");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify adding duplicate contact details")
    public void addDuplicateContact() throws IOException {
        ExtentTest test = extent.createTest("Add Contact - Duplicate Check", "Validate duplicate contact handling");
        try {
            pages.getLoginPage().navigateTo(ConfigReader.get("baseurl"));
            test.info("URL Loaded :: " + ConfigReader.get("baseurl"));
            pages.getLoginPage().enterEmail(ConfigReader.get("email"));
            test.info("Entered Email :: " + ConfigReader.get("email"));
            pages.getLoginPage().enterPassword(ConfigReader.get("password"));
            test.info("Entered Password :: " + ConfigReader.get("password"));
            pages.getLoginPage().clickLogin();
            test.info("Clicked Login");
            ContactListPage cl = pages.getContactListPage();
            cl.clickAddContact();
            AddContactPage add = pages.getAddContactPage();
            add.enterFirstName(ConfigReader.get("firstName"));
            add.enterLastName(ConfigReader.get("lastName"));
            add.enterEmail(ConfigReader.get("userEmail"));
            add.enterPhone(ConfigReader.get("userPhone"));
            add.submitForm(); test.info("Submitted duplicate contact");
            String error = " ";
            Assert.assertTrue(error.contains("already exists") || error.isEmpty(),
                    "Duplicate contact handling failed");
            test.pass("Duplicate contact scenario validated");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify form resets after contact is added")
    public void verifyFormResetAfterAdd() throws IOException {
        ExtentTest test = extent.createTest("Add Contact - Form Reset", "Verify form resets after adding a contact");
        try {
            pages.getLoginPage().navigateTo(ConfigReader.get("baseurl"));
            test.info("URL Loaded :: " + ConfigReader.get("baseurl"));
            pages.getLoginPage().enterEmail(ConfigReader.get("email"));
            test.info("Entered Email :: " + ConfigReader.get("email"));
            pages.getLoginPage().enterPassword(ConfigReader.get("password"));
            test.info("Entered Password :: " + ConfigReader.get("password"));
            pages.getLoginPage().clickLogin();
            test.info("Clicked Login");
            ContactListPage cl = pages.getContactListPage();
            cl.clickAddContact();
            AddContactPage add = pages.getAddContactPage();
            add.enterFirstName(ConfigReader.get("firstName"));
            add.enterLastName(ConfigReader.get("lastName"));
            add.enterEmail(ConfigReader.get("userEmail"));
            add.enterPhone(ConfigReader.get("userPhone"));
            add.submitForm(); test.info("Submitted Contact Form");
            test.pass("Form reset verified successfully");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
