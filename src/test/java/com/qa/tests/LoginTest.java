package com.qa.tests;

import com.qa.base.AppFactory;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class LoginTest extends AppFactory {
    private LoginPage loginPage;
    private ProductsPage productPage;
    private InputStream inputStream;
    private JSONObject loginUser;

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

    @Test(dataProvider = "userCredentials")
    public void verifyLogin(String userName, String password, String userType) {
        utilities.log().info("Verifying login for username: {}, password: {}", userName, password);
        loginPage.enterUserName(userName);
        loginPage.enterPassword(password);
        productPage = loginPage.clickLoginButton();

        if (userType.equals("invalid")) {
            validateInvalidLogin();
        } else {
            validateValidLogin();
        }
    }

    @DataProvider(name = "userCredentials")
    public Object[][] getUserCredentials() {
        return new Object[][]{
                {loginUser.getJSONObject("invalidUser").getString("userName"),
                        loginUser.getJSONObject("invalidUser").getString("password"), "invalid"},

                {loginUser.getJSONObject("invalidPassword").getString("userName"),
                        loginUser.getJSONObject("invalidPassword").getString("password"), "invalid"},

                {loginUser.getJSONObject("validUserAndPassword").getString("userName"),
                        loginUser.getJSONObject("validUserAndPassword").getString("password"), "valid"}
        };
    }

    private void validateInvalidLogin() {
        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = stringHashMap.get("error_invalid_userName_and_password");
        utilities.log().info("Actual Error Message: {}\nExpected Error Message: {}", actualErrorMessage, expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    private void validateValidLogin() {
        String actualProductTitle = productPage.getTitle();
        String expectedProductTitle = stringHashMap.get("product_title");
        utilities.log().info("Actual Product Page Title: {}\nExpected Product Page Title: {}", actualProductTitle, expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }

    private void loadDataFromFile() throws IOException {
        String dataFileName = "data/loginUsers.json";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName)) {
            JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputStream));
            loginUser = new JSONObject(jsonTokener);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
