package com.test.base;

import com.test.actions.Actions;
import com.test.util.Constants;
import com.test.util.reporter.Reporter;
import io.appium.java_client.ios.IOSDriver;
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

    private void setupIOsDriver(String hubUrl, DesiredCapabilities capabilities) throws IOException {
        driver = new IOSDriver(new URL(hubUrl), capabilities);
        driver.manage().timeouts().implicitlyWait(Constants.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }


    private void closeBrowser() {
        if (driver != null)
            driver.quit();
    }

    @Test(priority = -1)
    public void openAmazonPage() {
        Actions.mainActions().openMainPage();
    }

    @BeforeClass
    @Parameters({"hubUrl", "deviceName", "platformName",
            "platformVersion", "browserName", "orientation",
            "noReset", "udid", "accessKey", "username"})
    public void startBrowser(
            String hubUrl, String deviceName, String platformName, String platformVersion,
            String browserName, String orientation, String noReset,
            String udid, @Optional String accessKey, @Optional String username) throws IOException {

        String sauceHubUrl = String.format(hubUrl, username, accessKey);
        String message = "* Starting test " + this.getClass().toString();
        Reporter.log("\n" + message);
        System.out.println(message);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("orientation", orientation);
        capabilities.setCapability("noReset", noReset);
        capabilities.setCapability("udid", udid);

        String browser = Constants.DEFAULT_BROWSER;

        if (browser.equalsIgnoreCase("mobile_chrome")) {
            setupRemoteDriver(hubUrl, capabilities);
        } else if (browser.equalsIgnoreCase("mobile_safari")) {
            setupIOsDriver(sauceHubUrl, capabilities);
        } else throw new IllegalArgumentException(String.format("Browser type '%s' is unidentified...", browser));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        Reporter.log("Stopping WebDriver");
        closeBrowser();
    }
}
