package com.qa.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import com.qa.reports.ExtentReport;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
                .assignCategory(AppDriver.getPlatform() + " - " + AppDriver.getDeviceName())
                .assignAuthor("Saad");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReport.getTest().log(Status.PASS, "Test Passed successfully cheers!!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logTestFailure(result);
        try {
            takeScreenshotAndAttachToReport(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }

    @Override
    public void onStart(ITestContext context) {
        // Not implemented
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReport.getExtentReports().flush();
    }

    private void logTestFailure(ITestResult result) {
        if (result.getThrowable() != null) {
            StringWriter stringWriter = new StringWriter();
            result.getThrowable().printStackTrace(new PrintWriter(stringWriter));
            System.out.println(stringWriter.toString());
            ExtentReport.getTest().fail(result.getThrowable());
        }
    }

    private void takeScreenshotAndAttachToReport(ITestResult result) throws IOException {
        File screenshotFile = ((TakesScreenshot) AppDriver.getDriver()).getScreenshotAs(OutputType.FILE);
        String encodedScreenshot = encodeFileToBase64(screenshotFile);
        String completeImagePath = saveScreenshot(result, screenshotFile);

        ExtentReport.getTest().fail("Test Failed",
                MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
        if (encodedScreenshot != null) {
            ExtentReport.getTest().fail("Test Failed",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(encodedScreenshot).build());
        }
    }

    private String encodeFileToBase64(File file) {
        try {
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            return new String(encoded, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String saveScreenshot(ITestResult result, File screenshotFile) {
        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "screenshots" + File.separator + params.get("platformName") + "_" +
                params.get("platformVersion") + "_" + params.get("deviceName") + File.separator +
                AppFactory.getDateAndTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() +
                File.separator + result.getName() + ".png";

        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        try {
            FileUtils.copyFile(screenshotFile, new File(completeImagePath));
            Reporter.log("This is the sample screenshot");
            Reporter.log("<a href='" + completeImagePath + "'> <img src='" + completeImagePath +
                    "' height='100' width='100'/> </a>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return completeImagePath;
    }
}
