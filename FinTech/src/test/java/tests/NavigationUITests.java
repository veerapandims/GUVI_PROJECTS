package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationUITests extends BaseTest {

    @Test(description = "Navigate through Home, Accounts, Transfer, Loan, Contact pages")
    public void testNavigationLinks() {
        ExtentTest test = extent.createTest("Navigation - Links", "Verify navigation through top links");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            pages.getNavigationUIPage().clickHome(); test.info("Clicked Home link");
            pages.getNavigationUIPage().clickAccounts(); test.info("Clicked Accounts link");
            pages.getNavigationUIPage().clickTransfer(); test.info("Clicked Transfer link");
            pages.getNavigationUIPage().clickLoan(); test.info("Clicked Loan link");
            pages.getNavigationUIPage().clickContact(); test.info("Clicked Contact link");

            String screenshot = pages.getNavigationUIPage().takeScreenshot("Navigation_Links");
            test.pass("Navigation links verified successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Navigation links test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify logo redirects to Home page")
    public void testLogoRedirectsHome() {
        ExtentTest test = extent.createTest("Navigation - Logo Redirect", "Verify clicking logo goes to homepage");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            pages.getNavigationUIPage().clickLogo();
            String screenshot = pages.getNavigationUIPage().takeScreenshot("Logo_Home");
            test.pass("Logo redirects to home successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Logo redirect failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Validate visibility and clickability of all buttons")
    public void testButtonsVisibility() {
        ExtentTest test = extent.createTest("Navigation - Buttons", "Verify visibility and functionality of major buttons");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            Assert.assertTrue(pages.getNavigationUIPage().isButtonVisible(pages.getNavigationUIPage().submitBtn));
            Assert.assertTrue(pages.getNavigationUIPage().isButtonClickable(pages.getNavigationUIPage().submitBtn));

            Assert.assertTrue(pages.getNavigationUIPage().isButtonVisible(pages.getNavigationUIPage().transferBtn));
            Assert.assertTrue(pages.getNavigationUIPage().isButtonClickable(pages.getNavigationUIPage().transferBtn));

            String screenshot = pages.getNavigationUIPage().takeScreenshot("Buttons_Visibility");
            test.pass("All buttons visible and clickable", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Buttons visibility test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify consistent font and alignment across pages")
    public void testFontAndAlignment() {
        ExtentTest test = extent.createTest("Navigation - UI Consistency", "Verify consistent fonts and alignment");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            Assert.assertTrue(pages.getNavigationUIPage().isAligned(driver.findElement(pages.getNavigationUIPage().submitBtn)));
            Assert.assertTrue(pages.getNavigationUIPage().isAligned(driver.findElement(pages.getNavigationUIPage().transferBtn)));

            String screenshot = pages.getNavigationUIPage().takeScreenshot("UI_Consistency");
            test.pass("Fonts and alignment validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("UI consistency test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Validate alert boxes and popup messages")
    public void testAlertsAndPopups() {
        ExtentTest test = extent.createTest("Navigation - Alerts & Popups", "Verify alerts and popups show correct messages");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            Assert.assertTrue(pages.getNavigationUIPage().isAlertVisible(), "Alert should be visible");
            Assert.assertTrue(pages.getNavigationUIPage().getAlertText().length() > 0, "Alert should have text");

            Assert.assertTrue(pages.getNavigationUIPage().isToastVisible(), "Toast should be visible");
            Assert.assertTrue(pages.getNavigationUIPage().getToastMessage().length() > 0, "Toast should have message");

            String screenshot = pages.getNavigationUIPage().takeScreenshot("Alerts_Popups");
            test.pass("Alerts and popups validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Alerts and popups test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
