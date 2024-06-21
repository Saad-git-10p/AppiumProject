package com.qa.tests;

import com.qa.base.AppFactory;
import com.qa.pages.ProductsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.pages.LoginPage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class LoginTest extends AppFactory {

    private LoginPage loginPage;
    private ProductsPage productPage;
    private InputStream inputStream;
    private JSONObject loginUsers;

    @BeforeClass
    public void setupDataStream() throws IOException {
        try {
            String dataFileName = "data/loginUsers.json";
            inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputStream));
            loginUsers = new JSONObject(jsonTokener);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        closeApp();
        launchApp();
    }

    @BeforeMethod
    public void setup(Method method) {
        loginPage = new LoginPage();
        utilities.log().info("\n********** Starting Test: {} **********\n", method.getName());
    }

    @Test
    public void verifyInvalidUserName() {
        utilities.log().info("Verifying error message with invalid username");
        loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("userName"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
        loginPage.clickLoginButton();

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = stringHashMap.get("error_invalid_userName_and_password");
        utilities.log().info("Actual Error Message: {}\nExpected Error Message: {}", actualErrorMessage, expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void verifyInvalidPassword() {
        utilities.log().info("Verifying error message with invalid password");
        loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("userName"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
        loginPage.clickLoginButton();

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = stringHashMap.get("error_invalid_userName_and_password");
        utilities.log().info("Actual Error Message: {}\nExpected Error Message: {}", actualErrorMessage, expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void verifyUserCreation() {
        utilities.log().info("Verifying successful login with valid username and password");
        loginPage.enterUserName(loginUsers.getJSONObject("validUserAndPassword").getString("userName"));
        loginPage.enterPassword(loginUsers.getJSONObject("validUserAndPassword").getString("password"));
        productPage = loginPage.clickLoginButton();

        String actualProductTitle = productPage.getTitle();
        String expectedProductTitle = stringHashMap.get("product_title");
        utilities.log().info("Actual Product Page Title: {}\nExpected Product Page Title: {}", actualProductTitle, expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }
    @Test
    public void verifyzlogout(){
        utilities.log().info("Verifying successful logout scenario");
        loginPage.logOut();

    }
}
