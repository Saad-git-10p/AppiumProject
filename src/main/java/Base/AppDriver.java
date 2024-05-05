package Base;

import org.openqa.selenium.WebDriver;

public class AppDriver {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();// it is used to create separate driver for each thread

    public static  WebDriver getDriver(){
        return driver.get();
    }
   public static void  setDriver(WebDriver Driver){
        driver.set(Driver);
        System.out.println("Driver is set");

   }
}
