package com.qa.pages;

import com.qa.base.AppDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends MenuPage {

    public ProductsPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()), this);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PRODUCTS\"]")
    private WebElement productHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"test-Item title\" and @text=\"Sauce Labs Backpack\"]")
    private WebElement SLBackpackTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"test-Price\" and @text=\"$29.99\"]")
    private WebElement SLBackpackPrice;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Modal Selector Button']/android.view.ViewGroup/android.view.ViewGroup/android.widget.ImageView")
    private WebElement filterButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Price (low to high)']")
    private WebElement priceLowToHighOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='test-Item title' and @text='Sauce Labs Onesie']")
    private WebElement firstProductTitle;




    public void clickFilterButton() {
        filterButton.click();
    }

    public void selectPriceLowToHigh() {
        priceLowToHighOption.click();
    }

    public String getFirstProductTitle() {
        return firstProductTitle.getText();
    }
    public String getTitle() {
        return getText(productHeader, "Product Page Title is: ");
    }

    public String getSLBackpackTitle() {
        return getText(SLBackpackTitle, "Title is: ");
    }

    public String getSLBackpackPrice() {
        return getText(SLBackpackPrice, "Product Price is: ");
    }

    public ProductDetailsPage clickSLBackpackTitle() {
        clickElement(SLBackpackTitle, "Clicking on SLB Title link");
        return new ProductDetailsPage();
    }
}
