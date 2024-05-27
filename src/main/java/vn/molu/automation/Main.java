package vn.molu.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        System.out.println("Launching FIREFOX browser...");
        // URL của Selenium Server (Hub)
        String seleniumServerUrl = "http://10.39.68.174:4444/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("firefox");
//        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");

        WebDriver driver = new RemoteWebDriver(new URL(seleniumServerUrl), caps);
        driver.manage().window().maximize();
        driver.navigate().to("http://google.com.vn");
        System.out.println("Title: " + driver.getTitle());
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        Thread.sleep(5000);

        driver.quit();

    }
}
