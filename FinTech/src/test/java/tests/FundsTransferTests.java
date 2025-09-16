package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FundsTransferTests extends BaseTest {

    @Test(description = "Transfer funds between valid internal accounts")
    public void testValidFundsTransfer() {
        ExtentTest test = extent.createTest("Funds Transfer - Valid", "Verify successful transfer between accounts");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            pages.getFundsTransferPage().isAccountSummaryVisible();
            pages.getFundsTransferPage().clickFundTransferLink();
            pages.getFundsTransferPage().selectFromAccount("Checking");
            pages.getFundsTransferPage().selectToAccount("Savings");
            pages.getFundsTransferPage().enterAmount("100");
            pages.getFundsTransferPage().clickTransfer();
            Assert.assertTrue(pages.getFundsTransferPage().getConfirmationMessage().contains("Transfer Complete!"));
            String screenshot = pages.getFundsTransferPage().takeScreenshot("Funds_Transfer_Valid");
            test.pass("Funds transferred successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Funds transfer failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Transfer funds with insufficient balance")
    public void testInsufficientBalanceTransfer() {
        ExtentTest test = extent.createTest("Funds Transfer - Insufficient Balance", "Verify insufficient balance error");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            pages.getFundsTransferPage().isAccountSummaryVisible();
            pages.getFundsTransferPage().clickFundTransferLink();
            pages.getFundsTransferPage().selectFromAccount("Checking");
            pages.getFundsTransferPage().selectToAccount("Savings");
            pages.getFundsTransferPage().enterAmount("134134314324132432414412231");
            pages.getFundsTransferPage().clickTransfer();
            Assert.assertEquals(pages.getFundsTransferPage().getErrorMessage(), "An internal error has occurred and has been logged.");
            String screenshot = pages.getFundsTransferPage().takeScreenshot("Funds_Transfer_Insufficient");
            test.pass("Proper error displayed for insufficient balance", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Funds transfer insufficient balance test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Transfer funds with empty fields")
    public void testEmptyFieldsTransfer() {
        ExtentTest test = extent.createTest("Funds Transfer - Empty Fields", "Verify validation errors on empty fields");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            pages.getFundsTransferPage().isAccountSummaryVisible();
            pages.getFundsTransferPage().clickFundTransferLink();
            pages.getFundsTransferPage().clickTransfer();
            Assert.assertEquals(pages.getFundsTransferPage().getErrorMessage(), "An internal error has occurred and has been logged.");
            String screenshot = pages.getFundsTransferPage().takeScreenshot("Funds_Transfer_Empty");
            test.pass("Validation errors displayed for empty fields", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Funds transfer empty fields test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Attempt to transfer to the same account")
    public void testSameAccountTransfer() {
        ExtentTest test = extent.createTest("Funds Transfer - Same Account", "Verify same account transfer is blocked");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            pages.getFundsTransferPage().isAccountSummaryVisible();
            pages.getFundsTransferPage().clickFundTransferLink();
            pages.getFundsTransferPage().selectFromAccount("Checking");
            pages.getFundsTransferPage().selectToAccount("Checking");
            pages.getFundsTransferPage().enterAmount("50");
            pages.getFundsTransferPage().clickTransfer();
            Assert.assertEquals(pages.getFundsTransferPage().getErrorMessage(), "Cannot transfer to the same account.");
            String screenshot = pages.getFundsTransferPage().takeScreenshot("Funds_Transfer_SameAccount");
            test.pass("Error displayed for same account transfer", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Funds transfer same account test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Transfer large or decimal amount")
    public void testLargeOrDecimalTransfer() {
        ExtentTest test = extent.createTest("Funds Transfer - Large/Decimal", "Verify handling of large and decimal amounts");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            pages.getFundsTransferPage().isAccountSummaryVisible();
            pages.getFundsTransferPage().clickFundTransferLink();
            pages.getFundsTransferPage().selectFromAccount("Checking");
            pages.getFundsTransferPage().selectToAccount("Savings");
            pages.getFundsTransferPage().enterAmount("12345.67");
            pages.getFundsTransferPage().clickTransfer();
            Assert.assertTrue(pages.getFundsTransferPage().getConfirmationMessage().contains("Transfer Complete!"));
            String screenshot = pages.getFundsTransferPage().takeScreenshot("Funds_Transfer_LargeDecimal");
            test.pass("Large/decimal transfer processed successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Funds transfer large/decimal test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Validate transfer confirmation and updated balances")
    public void testTransferConfirmationAndBalances() {
        ExtentTest test = extent.createTest("Funds Transfer - Confirmation & Balance Update", "Verify balance updates after transfer");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            pages.getFundsTransferPage().isAccountSummaryVisible(); test.info("Account summary visible");
            pages.getFundsTransferPage().clickFundTransferLink();test.info("Clicked Fund Transfer Link");
            pages.getFundsTransferPage().selectFromAccount("Checking");
            pages.getFundsTransferPage().selectToAccount("Savings");
            pages.getFundsTransferPage().enterAmount("200");
            pages.getFundsTransferPage().clickTransfer();
            Assert.assertTrue(pages.getFundsTransferPage().getConfirmationMessage().contains("Transfer Complete!"));
            String screenshot = pages.getFundsTransferPage().takeScreenshot("Funds_Transfer_Confirmation");
            test.pass("Transfer confirmation and balances validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Funds transfer confirmation test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
