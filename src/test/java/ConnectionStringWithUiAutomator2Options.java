import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionStringWithUiAutomator2Options {
    AppiumDriver driver;

    @BeforeTest
    public void initializer() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setAutomationName("UiAutomator2")
                .setApp(System.getProperty("user.dir") + "/app/ApiDemos-debug.apk");
        URL url = new URL("http://localhost:4723");
        driver = new AndroidDriver(url, options);
        System.out.println("Opening Appium Server");
    }

    @Test
    public void sampleTest() {
        System.out.print("This is a 1st Sample Test");
    }

    @AfterTest
    public void teaDown() {
        if (null != driver) {
            driver.quit();
        }
    }
}
