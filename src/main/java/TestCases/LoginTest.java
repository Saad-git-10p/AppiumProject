package TestCases;
import Base.AppFactory;
import Pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginTest {
    LoginPage loginPage;
    @BeforeMethod
    public void setup(){
        loginPage = new LoginPage();
    }
    @Test
    public void verifyValidUserLogin() throws InterruptedException {
        System.out.println("Logging in with username and password");
        loginPage.enterValidUserName("standard_user");
        loginPage.enterValidPassword("secret_sauce");
        loginPage.clickLoginButton();
        System.out.println("Login Passed");
        Thread.sleep(3000);


    }
    @AfterMethod
    public void tearDown(){
        AppFactory.quiteDriver();
    }
}



