package com.qa.tests;

import com.qa.base.AppFactory;
import com.qa.pages.ProductsPage;
import com.qa.pages.LoginPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class LoginLogoutTest extends AppFactory {

    private LoginPage loginPage;
    private ProductsPage productPage;
    private InputStream inputStream;
    private JSONObject loginUsers;

    @BeforeClass
    public void setupDataStream() throws IOException {
        loadDataFromFile();
        closeApp();
        launchApp();
    }

    @BeforeMethod
    public void setup(Method method) {
        loginPage = new LoginPage();
        utilities.log().info("\n********** Starting Test: {} **********\n", method.getName());
    }

    @Test(priority = 1)
    public void verifyInvalidUserName() {
        utilities.log().info("Verifying error message for invalid username");
        loginWithCredentials("invalidUser");

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = stringHashMap.get("error_invalid_userName_and_password");
        utilities.log().info("Actual Error Message: {}\nExpected Error Message: {}", actualErrorMessage, expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(priority = 2)
    public void verifyInvalidPassword() {
        utilities.log().info("Verifying error message for invalid password");
        loginWithCredentials("invalidPassword");

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = stringHashMap.get("error_invalid_userName_and_password");
        utilities.log().info("Actual Error Message: {}\nExpected Error Message: {}", actualErrorMessage, expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(priority = 3)
    public void verifyValidUser() {
        utilities.log().info("Verifying successful login with valid username and password");
        loginWithCredentials("validUserAndPassword");

        String actualProductTitle = productPage.getTitle();
        String expectedProductTitle = stringHashMap.get("product_title");
        utilities.log().info("Actual Product Page Title: {}\nExpected Product Page Title: {}", actualProductTitle, expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }

    @Test (priority = 4)
    public void verifyLogout() {
        utilities.log().info("Verifying successful logout");
        loginPage.logOut();
        // Add assertions to verify logout was successful if applicable
    }

    private void loginWithCredentials(String userType) {
        String userName = loginUsers.getJSONObject(userType).getString("userName");
        String password = loginUsers.getJSONObject(userType).getString("password");

        loginPage.enterUserName(userName);
        loginPage.enterPassword(password);
        productPage = loginPage.clickLoginButton();
    }

    private void loadDataFromFile() throws IOException {
        String dataFileName = "data/loginUsers.json";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName)) {
            JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputStream));
            loginUsers = new JSONObject(jsonTokener);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
