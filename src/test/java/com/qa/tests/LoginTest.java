package com.qa.tests;

import com.qa.base.AppFactory;
import com.qa.pages.ProductPage;
import netscape.javascript.JSObject;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.pages.LoginPage;



public class LoginTest extends AppFactory {

    LoginPage loginPage;
    ProductPage productPage;
    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage();
    }
    @Test
    public void verifyInvalidUsername(){
        System.out.println("Test to verify Invalid username and get error message");
        loginPage.enterValidUserName("invalidUser");
        loginPage.enterValidPassword("secret_sauce");
        loginPage.clickLoginButton();
        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Username and password do not match any user in this service.";
        System.out.println("Actual error message is"+ actualErrorMessage+"Expected message is"+expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);

    }
    @Test
    public void verifyInvalidPassword(){
        System.out.println("Test to verify valid username and Invalid Password and get error message");
        loginPage.enterValidUserName("standard_user");
        loginPage.enterValidPassword("invalidPass");
        loginPage.clickLoginButton();
        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Username and password do not match any user in this service.";
        System.out.println("Actual error message is"+ actualErrorMessage+"Expected message is"+expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);
    }
    @Test
    public void verifyValidUserLogin(){
        System.out.println("Test to verify valid username and Invalid Password and get error message");
        loginPage.enterValidUserName("standard_user");
        loginPage.enterValidPassword("secret_sauce");
      //  productPage = new loginPage.clickLoginButton();
        String actualProductTitle = productPage.getTitle();
        String expectedProductTitle = "PRODUCTS";
        System.out.println("Actual Product page title is - " + actualProductTitle + "\n" + "Expected Product page title is - " + expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);



    }

}
