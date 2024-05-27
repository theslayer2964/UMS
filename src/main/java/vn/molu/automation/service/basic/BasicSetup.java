package vn.molu.automation.service.basic;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
public class BasicSetup {
    private WebDriver driver;

    public WebDriver getDriver(String appURL, String driverLocation) {
        initializeTestBaseSetup("firefox", appURL, driverLocation);
        return driver;
    }

    private void setDriver(String browserType, String appURL, String driverLocation) {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL, driverLocation);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL, driverLocation);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver(appURL, driverLocation);
        }
    }

    private WebDriver initFirefoxDriver(String appURL, String driverLocation) {
        try {
            System.out.println("Launching firefox browser..." + driverLocation);
//            System.setProperty("webdriver.gecko.driver", driverLocation);
//            WebDriver driver = new FirefoxDriver();
            FirefoxOptions options = new FirefoxOptions();
            WebDriver driver = new RemoteWebDriver(new URL(driverLocation), options);
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

    private WebDriver initChromeDriver(String appURL, String driverLocation) {
        try {
            System.out.println("Launching chrome browser... " + driverLocation);
//            System.setProperty("webdriver.chrome.driver", driverLocation);
//            String seleniumHubUrl = "http://10.151.99.46:4444/wd/hub";
            ChromeOptions options = new ChromeOptions();
            WebDriver driver = new RemoteWebDriver(new URL(driverLocation), options);
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

    public void initializeTestBaseSetup(String browserType, String appURL, String driverLocation) {
        try {
            // Khởi tạo driver và browser
            setDriver(browserType, appURL, driverLocation);
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
