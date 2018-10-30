package com.test.base;

import com.test.util.Constants;
import com.test.util.reporter.Reporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITest;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class BaseTest implements ITest {

    public static WebDriver driver;

    private String currentAnnotatedMethodName = "";

    @AfterMethod
    public void setNullTestName() {
        currentAnnotatedMethodName = null;
    }

    @Override
    public String getTestName() {
        return currentAnnotatedMethodName;
    }

    @BeforeMethod
    public void setTestName(Method method) {
        currentAnnotatedMethodName = "";
        try {
            String testName = method.getAnnotation(Test.class).testName();
            if (!testName.isEmpty())
                currentAnnotatedMethodName = testName;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void setupRemoteDriver(String hubUrl, DesiredCapabilities capabilities) throws IOException {
        driver = new CustomRemoteWebDriver(new URL(hubUrl), capabilities);
        driver.manage().timeouts().implicitlyWait(Constants.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }


    private void closeBrowser() {
        if (driver != null)
            driver.quit();
    }


    @BeforeClass
    @Parameters({"hubUrl", "deviceName", "platformName", "platformVersion", "udid", "browserName", "orientation", "noReset"})
    public void startBrowser(String hubUrl, String deviceName, String platformName, String platformVersion,
                             String udid, String browserName, String orientation, String noReset) throws IOException {

        String message = "* Starting test " + this.getClass().toString();
        Reporter.log("\n" + message);
        System.out.println(message);

        DesiredCapabilities capabilities = DesiredCapabilities.android();
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("orientation", orientation);
        capabilities.setCapability("noReset", noReset);

        String browser = Constants.DEFAULT_BROWSER;

        if (browser.equalsIgnoreCase("mobile_chrome"))
            setupRemoteDriver(hubUrl, capabilities);
        else throw new IllegalArgumentException();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        Reporter.log("Stopping WebDriver");
        closeBrowser();
    }


}
