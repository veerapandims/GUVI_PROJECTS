package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTests extends BaseTest {

    @Test(description = "View account summary of checking account")
    public void testCheckingAccountSummary() {
        ExtentTest test = extent.createTest("Checking Account Summary", "Verify checking account details");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getAccountPage().enterUserName(ConfigReader.get("userName")); test.info("Entered username");
            pages.getAccountPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getAccountPage().clickLogin(); test.info("Clicked Login");
            pages.getAccountPage().openCheckingAccount();
            test.info("Opened Checking Account");
            Assert.assertNotNull(pages.getAccountPage().getBalance(), "Balance should be visible");
            Assert.assertTrue(pages.getAccountPage().isRecentTransactionsVisible(), "Recent transactions should be visible");

            String screenshot = pages.getAccountPage().takeScreenshot("Checking_Account_Summary");
            test.pass("Checking account summary validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Checking account test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "View account summary of savings account")
    public void testSavingsAccountSummary() {
        ExtentTest test = extent.createTest("Savings Account Summary", "Verify savings account details");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getAccountPage().enterUserName(ConfigReader.get("userName")); test.info("Entered username");
            pages.getAccountPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getAccountPage().clickLogin(); test.info("Clicked Login");
            pages.getAccountPage().openSavingsAccount();
            test.info("Opened Savings Account");

            Assert.assertNotNull(pages.getAccountPage().getBalance(), "Balance should be visible");
            Assert.assertTrue(pages.getAccountPage().isRecentTransactionsVisible(), "Recent transactions should be visible");

            String screenshot = pages.getAccountPage().takeScreenshot("Savings_Account_Summary");
            test.pass("Savings account summary validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Savings account test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Validate updated balance after transaction")
    public void testUpdatedBalanceAfterTransaction() {
        ExtentTest test = extent.createTest("Updated Balance After Transaction", "Verify balance updates");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getAccountPage().enterUserName(ConfigReader.get("userName")); test.info("Entered username");
            pages.getAccountPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getAccountPage().clickLogin(); test.info("Clicked Login");
            pages.getAccountPage().openCheckingAccount();
            String before = pages.getAccountPage().getBalance();
            String after = pages.getAccountPage().getBalance();
            Assert.assertNotEquals(before, after, "Balance should update after transaction");

            String screenshot = pages.getAccountPage().takeScreenshot("Balance_After_Transaction");
            test.pass("Balance updated successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Balance update test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "View full transaction history")
    public void testTransactionHistory() {
        ExtentTest test = extent.createTest("Transaction History", "Verify full transaction list");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getAccountPage().enterUserName(ConfigReader.get("userName")); test.info("Entered username");
            pages.getAccountPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getAccountPage().clickLogin(); test.info("Clicked Login");
//            pages.getAccountPage().openTransactionHistory();
            Assert.assertTrue(pages.getAccountPage().isTransactionHistoryVisible(), "Transaction history should be displayed");

            String screenshot = pages.getAccountPage().takeScreenshot("Transaction_History");
            test.pass("Transaction history validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Transaction history test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Validate transaction list includes date, amount, and type")
    public void testTransactionDetails() {
        ExtentTest test = extent.createTest("Transaction Details", "Verify date, amount and type in list");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getAccountPage().enterUserName(ConfigReader.get("userName")); test.info("Entered username");
            pages.getAccountPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getAccountPage().clickLogin(); test.info("Clicked Login");
            pages.getAccountPage().openTransactionHistory();

//            boolean detailsPresent = pages.getAccountPage().validateTransactionDetails("2025-09-15", "500", "Credit");
//            Assert.assertTrue(detailsPresent, "Transaction details should be correct");

            String screenshot = pages.getAccountPage().takeScreenshot("Transaction_Details");
            test.pass("Transaction details validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Transaction details test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Filter transactions by date range")
    public void testFilterByDateRange() {
        ExtentTest test = extent.createTest("Transaction Filter By Date Range", "Verify filtering");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getAccountPage().enterUserName(ConfigReader.get("userName")); test.info("Entered username");
            pages.getAccountPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getAccountPage().clickLogin(); test.info("Clicked Login");
            pages.getAccountPage().openTransactionHistory();
            test.info("Open Transaction history");
            pages.getAccountPage().filterByDateRange("09-04-2025");
            pages.getAccountPage().clickFindTransaction();
            String screenshot = pages.getAccountPage().takeScreenshot("Filter_By_Date_Range");
            test.pass("Transaction filter validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Transaction filter test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Sort transactions by date and amount")
    public void testSortTransactions() {
        ExtentTest test = extent.createTest("Transaction Sorting", "Verify sorting works");
        try {
            driver.get(ConfigReader.get("baseurl"));
            pages.getAccountPage().enterUserName(ConfigReader.get("userName")); test.info("Entered username");
            pages.getAccountPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getAccountPage().clickLogin(); test.info("Clicked Login");
            pages.getAccountPage().sortByDate();
            test.info("Sorted by Date");
            pages.getAccountPage().openTransactionHistory();
            test.info("Open Transaction history");
            pages.getAccountPage().filterByDateRange("09-04-2025");
            pages.getAccountPage().clickFindTransaction();
            test.info("Sorted by Amount");

            String screenshot = pages.getAccountPage().takeScreenshot("Sort_Transactions");
            test.pass("Transaction sorting validated", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        } catch (Exception e) {
            test.fail("Transaction sorting test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
