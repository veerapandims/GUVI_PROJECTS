package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SessionPage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SessionTests extends BaseTest {
    String url = ConfigReader.get("baseurl");
    String email = ConfigReader.get("email");
    String pwd = ConfigReader.get("password");
    @Test(description = "Verify logout redirects to login page")
    public void verifyLogoutRedirectsToLogin() throws IOException {
        ExtentTest test = extent.createTest("Verify logout redirects to login page",
                "User should be redirected to login page after logout");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            pages.getLoginPage().waitForVisiblity();test.info("Login Success");
            SessionPage session = pages.getSessionPage();
            session.clickLogout();
            test.info("Clicked Logout");
            session.waitForLoginPage();
            Assert.assertTrue(session.isOnLoginPage(), "User should be redirected to login page after logout");
            test.pass("Logout redirected to login page successfully",
                    MediaEntityBuilder.createScreenCaptureFromPath(session.takeScreenshot("Logout_Success")).build());
        } catch (Exception e) {
            String screenshotPath = pages.getSessionPage().takeScreenshot("Logout_Failure");
            test.fail("Logout test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Logout test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify login state persists after refresh")
    public void verifyLoginStateAfterRefresh() throws IOException {
        ExtentTest test = extent.createTest("Verify login state persists after refresh",
                "User should remain logged in after refreshing the page");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            pages.getLoginPage().waitForVisiblity();test.info("Login Success");
            SessionPage session = pages.getSessionPage();
            session.refreshPage();
            test.info("Refreshed the page");
            session.waitForContactListPage();
            Assert.assertTrue(session.isOnContactListPage(), "User should remain logged in after refresh");
            test.pass("Login state persisted after refresh",
                    MediaEntityBuilder.createScreenCaptureFromPath(session.takeScreenshot("Refresh_Login_State")).build());

        } catch (Exception e) {
            String screenshotPath = pages.getSessionPage().takeScreenshot("Refresh_Login_State_Failure");
            test.fail("Login state after refresh test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Login state after refresh test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify login required to access contact list directly")
    public void verifyDirectAccessToContactList() throws IOException {
        ExtentTest test = extent.createTest("Verify login required to access contact list directly",
                "User should be redirected to login page when accessing contact list without login");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            pages.getLoginPage().waitForVisiblity();test.info("Login Success");
            SessionPage session = pages.getSessionPage();
            session.navigateDirectToContactList(url);
            test.info("Navigated directly to contact list URL");
            session.waitForLoginPage();
            Assert.assertTrue(session.isOnLoginPage(), "User should be redirected to login page without login");
            test.pass("Direct access to contact list blocked successfully",
                    MediaEntityBuilder.createScreenCaptureFromPath(session.takeScreenshot("Direct_Access_Block")).build());

        } catch (Exception e) {
            String screenshotPath = pages.getSessionPage().takeScreenshot("Direct_Access_Block_Failure");
            test.fail("Direct access test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Direct access test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify back button after logout does not reopen contact list")
    public void verifyBackButtonAfterLogout() throws IOException {
        ExtentTest test = extent.createTest("Verify back button after logout",
                "User should remain on login page after navigating back post-logout");
        try {
            driver.get(url);
            pages.getLoginPage().enterEmail(email);test.info("Entered Email :: "+ email);
            pages.getLoginPage().enterPassword(pwd);test.info("Entered Password :: "+ pwd);
            pages.getLoginPage().clickLogin();test.info("Clicked Login");
            pages.getLoginPage().waitForVisiblity();test.info("Login Success");
            SessionPage session = pages.getSessionPage();
            session.clickLogout();
            test.info("Clicked Logout");
            session.waitForLoginPage();
            Assert.assertTrue(session.isOnLoginPage(), "User should be on login page after logout");
            session.navigateBack();
            test.info("Clicked browser back button");
            session.waitForLoginPage();
            Assert.assertTrue(session.isOnLoginPage(), "User should remain on login page after navigating back");
            test.pass("Back button behavior after logout verified",
                    MediaEntityBuilder.createScreenCaptureFromPath(session.takeScreenshot("Back_After_Logout")).build());

        } catch (Exception e) {
            String screenshotPath = pages.getSessionPage().takeScreenshot("Back_After_Logout_Failure");
            test.fail("Back button after logout test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            log.error("Back button after logout test failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
