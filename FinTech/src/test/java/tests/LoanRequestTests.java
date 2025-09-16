package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import listeners.ConfigReader;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Slf4j
public class LoanRequestTests extends BaseTest {

    @Test(description = "Submit loan request with valid details")
    public void testValidLoanRequest() throws IOException {
        ExtentTest test = extent.createTest("Loan Request - Valid", "Verify successful loan request submission");
        try {
            driver.get(ConfigReader.get("baseurl"));
            log.info("Navigated to base URL");
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");
            log.info("Login successful");

            pages.getFundsTransferPage().clickLoadRequestLink(); test.info("Clicked Loan Request link");
            pages.getLoanRequestPage().enterLoanAmount("5000"); test.info("Entered Loan Amount: 5000");
            pages.getLoanRequestPage().enterLoanTerm("24"); test.info("Entered Loan Term: 24");
            pages.getLoanRequestPage().clickSubmit(); test.info("Clicked Submit");

            Assert.assertTrue(pages.getLoanRequestPage().getSuccessMessage().contains("Loan Request Processed"));
            String screenshot = pages.getLoanRequestPage().takeScreenshot("LoanRequest_Valid");
            test.pass("Loan request submitted successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Loan request processed successfully");
        } catch (Exception e) {
            String screenshot = pages.getLoanRequestPage().takeScreenshot("LoanRequest_Valid_Fail");
            test.fail("Loan request valid submission failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Loan request valid submission failed", e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Submit loan request with missing information")
    public void testLoanRequestMissingFields() throws IOException {
        ExtentTest test = extent.createTest("Loan Request - Missing Fields", "Verify validation errors for missing info");
        try {
            driver.get(ConfigReader.get("baseurl"));
            log.info("Navigated to base URL");
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");

            pages.getFundsTransferPage().clickLoadRequestLink(); test.info("Clicked Loan Request link");
            pages.getLoanRequestPage().enterLoanAmount(""); test.info("Left Loan Amount blank");
            pages.getLoanRequestPage().enterLoanTerm(""); test.info("Left Loan Term blank");
            pages.getLoanRequestPage().clickSubmit(); test.info("Clicked Submit");

            Assert.assertTrue(pages.getLoanRequestPage().getErrorMessage().contains("Required fields"));
            String screenshot = pages.getLoanRequestPage().takeScreenshot("LoanRequest_Missing");
            test.pass("Validation error displayed for missing fields", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Validation error displayed for missing loan request fields");
        } catch (Exception e) {
            String screenshot = pages.getLoanRequestPage().takeScreenshot("LoanRequest_Missing_Fail");
            test.fail("Loan request missing fields test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Loan request missing fields test failed", e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Submit loan request exceeding limit")
    public void testLoanRequestExceedingLimit() throws IOException {
        ExtentTest test = extent.createTest("Loan Request - Exceeding Limit", "Verify error for exceeding loan limit");
        try {
            driver.get(ConfigReader.get("baseurl"));
            log.info("Navigated to base URL");
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");

            pages.getFundsTransferPage().clickLoadRequestLink(); test.info("Clicked Loan Request link");
            pages.getLoanRequestPage().enterLoanAmount("100000011231"); test.info("Entered huge Loan Amount");
            pages.getLoanRequestPage().enterLoanTerm("60"); test.info("Entered Loan Term: 60");
            pages.getLoanRequestPage().clickSubmit(); test.info("Clicked Submit");

            Assert.assertEquals(pages.getLoanRequestPage().getErrorMessage(), "Loan amount exceeds limit.");
            String screenshot = pages.getLoanRequestPage().takeScreenshot("LoanRequest_ExceedLimit");
            test.pass("Error displayed for exceeding loan limit", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Error correctly displayed for exceeding loan limit");
        } catch (Exception e) {
            String screenshot = pages.getLoanRequestPage().takeScreenshot("LoanRequest_ExceedLimit_Fail");
            test.fail("Loan request exceeding limit test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Loan request exceeding limit test failed", e);
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify loan request status and result")
    public void testLoanRequestStatus() throws IOException {
        ExtentTest test = extent.createTest("Loan Request - Status", "Verify loan application status");
        try {
            driver.get(ConfigReader.get("baseurl"));
            log.info("Navigated to base URL");
            pages.getFundsTransferPage().enterUserName(ConfigReader.get("userName")); test.info("Entered userName");
            pages.getFundsTransferPage().enterPassword(ConfigReader.get("password")); test.info("Entered Password");
            pages.getFundsTransferPage().clickLogin(); test.info("Clicked Login");

            pages.getFundsTransferPage().clickLoadRequestLink(); test.info("Clicked Loan Request link");
            pages.getLoanRequestPage().enterLoanAmount("5000"); test.info("Entered Loan Amount: 5000");
            pages.getLoanRequestPage().enterLoanTerm("24"); test.info("Entered Loan Term: 24");
            pages.getLoanRequestPage().clickSubmit(); test.info("Clicked Submit");

            String status = pages.getLoanRequestPage().getLoanStatus();
            Assert.assertTrue(status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Rejected"));
            String screenshot = pages.getLoanRequestPage().takeScreenshot("LoanRequest_Status");
            test.pass("Loan request status validated: " + status,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.info("Loan request status verified as: {}", status);
        } catch (Exception e) {
            String screenshot = pages.getLoanRequestPage().takeScreenshot("LoanRequest_Status_Fail");
            test.fail("Loan request status check failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            log.error("Loan request status test failed", e);
            throw new RuntimeException(e);
        }
    }
}
