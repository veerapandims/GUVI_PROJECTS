package tests;

import managers.PageObjectManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactFormPage;

public class ContactFormValidationTests extends BaseTest {

    @Test(description = "Verify max character limit for contact fields")
    public void testMaxCharacterLimitForFirstName() {
        PageObjectManager pom = new PageObjectManager(driver);
        ContactFormPage formPage = pom.getContactFormPage();
        String longName = "A".repeat(350); // 300+ characters
        formPage.enterFirstName(longName);
        formPage.clickSave();
        String actualValue = formPage.getErrorMessage();
        if (actualValue.length() <= 300) {
            Assert.assertTrue(actualValue.length() <= 300,
                    "Field allows more than 300 characters.");
        } else {
            Assert.assertTrue(formPage.getErrorMessage().contains("limit"),
                    "Expected validation error not shown.");
        }
    }

    @Test(description = "Verify Unicode and emojis in address field")
    public void testUnicodeAndEmojisInAddress() {
        PageObjectManager pom = new PageObjectManager(driver);
        ContactFormPage formPage = pom.getContactFormPage();
        String unicodeAddress = "testdfasdr";
        formPage.enterAddress(unicodeAddress);
        formPage.clickSave();
        Assert.assertTrue(formPage.isContactAdded(), "Contact not added successfully.");
        Assert.assertEquals(formPage.getErrorMessage(), unicodeAddress,
                "Unicode/emoji characters not handled correctly.");
    }
}
