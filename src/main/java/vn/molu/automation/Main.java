package vn.molu.automation;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Launching FIREFOX browser...");
            // URL của Selenium Server (Hub)
            String seleniumServerUrl = "http://10.151.99.46:8888/wd/hub";
            DesiredCapabilities caps = DesiredCapabilities.firefox();
            caps.setBrowserName("firefox");
            caps.setPlatform(Platform.ANY);
//        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");

            System.setProperty("webdriver.gecko.driver", "C:\\Users\\User\\Desktop\\Selenium\\geckodriver.exe");
            WebDriver driver = new RemoteWebDriver(new URL(seleniumServerUrl), caps);
            driver.manage().window().maximize();
            driver.navigate().to("http://google.com.vn");
            System.out.println("Title: " + driver.getTitle());
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            Thread.sleep(5000);

            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
