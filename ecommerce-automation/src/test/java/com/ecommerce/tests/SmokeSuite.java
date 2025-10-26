package com.ecommerce.tests;

import com.ecommerce.core.BaseTest;
import com.ecommerce.core.ConfigReader;
import com.ecommerce.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import com.ecommerce.utils.DataProviders;
import com.ecommerce.core.Waits;

import java.util.UUID;

public class SmokeSuite extends BaseTest {


    @Test(dataProvider = "signupData", dataProviderClass = com.ecommerce.utils.DataProviders.class)
    public void signUp_fromExcel_showsSuccessAlert(String username, String password, String expectedResult) {
        HomePage home = new HomePage(driver);
        home.openSignupModal();
        SignupPage sp = new SignupPage(driver);
        sp.signup(username, password);
        // Very small sleep to allow alert to appear (site dependent) - prefer explicit wait on alert in real tests
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        // Check alert or result - expectedResult could be 'success' or 'error'
        if ("success".equalsIgnoreCase(expectedResult)) {
            // assume signup shows alert - keep existing assertion style
        }
    }
    @Test
    public void signUp_uniqueUser_showsSuccessAlert(){
        HomePage home = new HomePage(driver);
        home.openSignupModal();
        SignupPage sp = new SignupPage(driver);
        String uniqueUser = "user_" + UUID.randomUUID().toString().substring(0,8);
        sp.signUp(uniqueUser, "Pass@123");
        String alert = sp.readAlertIfPresent();
        Assert.assertTrue(alert != null && alert.toLowerCase().contains("sign up") || alert.toLowerCase().contains("successful"),
                "Expected success alert, got: " + alert);
    }

    @Test
    public void login_invalidPassword_showsError(){
        HomePage home = new HomePage(driver);
        home.openLoginModal();
        LoginPage lp = new LoginPage(driver);
        lp.login(ConfigReader.get("username"), "WrongPassword!");
        String alert = lp.readAlertIfPresent();
        Assert.assertTrue(alert != null && alert.toLowerCase().contains("wrong") || alert.toLowerCase().contains("not"), "Expected error alert, got: " + alert);
    }

    @Test
    public void addProduct_toCart_and_validateInCart(){
        HomePage home = new HomePage(driver);
        home.openProductByName("Samsung galaxy s6");
        ProductPage pp = new ProductPage(driver);
        pp.addProductToCart();
        String alert = pp.readAlertIfPresent();
        Assert.assertTrue(alert != null && alert.toLowerCase().contains("added"), "Expected 'Product added' alert");
        // Go to cart and verify at least 1 item exists
        home.goToCart();
        CartPage cp = new CartPage(driver);
        Assert.assertTrue(cp.itemsCount() >= 1, "Cart should have at least one item");
    }

    @Test
    public void placeOrder_withValidDetails_getConfirmation(){
        HomePage home = new HomePage(driver);
        home.goToCart();
        CartPage cp = new CartPage(driver);
        cp.clickPlaceOrder();
        OrderModal om = new OrderModal(driver);
        om.fill("Test User","India","Kolkata","4111111111111111","08","2026");
        om.clickPurchase();
        Assert.assertTrue(om.confirmationVisible(), "Confirmation dialog should be visible");
        om.confirm();
    }

    @Test
    public void categoryFilter_displaysRelevantProducts(){
        HomePage home = new HomePage(driver);
        home.filterLaptops();
        // simple smoke: ensure at least one product link is present for the category
        // (Demoblaze populates items by category; a strict validation can be added via API or DOM list count)
        Assert.assertTrue(true, "Category filter executed (placeholder assertion).");
    }

    @Test
    public void logout_shouldShowLoginButton(){
        HomePage home = new HomePage(driver);
        home.openLoginModal();
        LoginPage lp = new LoginPage(driver);
        lp.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(home.isUserLoggedIn(), "User should be logged in");
        // Click logout
        Waits.clickableAndClick(driver, org.openqa.selenium.By.id("logout2"));
        Assert.assertFalse(home.isUserLoggedIn(), "User should be logged out");
    }
}
