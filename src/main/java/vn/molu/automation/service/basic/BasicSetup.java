package vn.molu.automation.service.basic;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
public class BasicSetup {
    private WebDriver driver;

    public WebDriver getDriver(String appURL, String driverLocation, String seleniumServer) {
        initializeTestBaseSetup("firefox", appURL, driverLocation, seleniumServer);
        return driver;
    }

    private void setDriver(String browserType, String appURL, String driverLocation, String seleniumServer) {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL, driverLocation, seleniumServer);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL, driverLocation, seleniumServer);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver(appURL, driverLocation, seleniumServer);
        }
    }

    private WebDriver initFirefoxDriver(String appURL, String driverLocation, String seleniumServer) {
        try {
//            System.out.println("Launching firefox browser..." + driverLocation);
//            FirefoxOptions options = new FirefoxOptions();
//            WebDriver driver = new RemoteWebDriver(new URL(seleniumServer), options);
            System.out.println("Launching Chrome - local - browser...: " + driverLocation);
            ///
            System.setProperty("webdriver.gecko.driver", driverLocation);
            WebDriver driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.navigate().to(appURL);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private WebDriver initChromeDriver(String appURL, String driverLocation, String seleniumServer) {
        try {
            System.out.println("Launching chrome browser... " + driverLocation);
            System.setProperty("webdriver.chrome.driver", driverLocation);
            ChromeOptions options = new ChromeOptions();
            WebDriver driver = new RemoteWebDriver(new URL(seleniumServer), options);
            driver.manage().window().maximize();
            driver.navigate().to(appURL);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initializeTestBaseSetup(String browserType, String appURL, String driverLocation, String seleniumServer) {
        try {
            // Khởi tạo driver và browser
            setDriver(browserType, appURL, driverLocation, seleniumServer);
        } catch (Exception e) {
//            System.out.println("Error...:");
            e.getStackTrace();
        }
    }

    public void tearDown() throws Exception {
        Thread.sleep(2000);
        driver.quit();
    }
}
