package vn.molu.webapp.controller.api;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {
    @Value("${system.selenium-server.path}")
    private String seleniumServer;
    @Value("${system.firefox.driver}")
    private String firefoxLocationUrl;

    @GetMapping("/api/user.html")
    public String list() {
        System.out.println("======================");
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/example")
    public String example() throws InterruptedException, MalformedURLException {
        System.out.println("Launching Chrome browser...");
//        String seleniumHubUrl = "http://10.151.99.46:4444/wd/hub";
        FirefoxOptions options = new FirefoxOptions();
//        System.setProperty("webdriver.chrome.driver", chromeLocationUrl);
        WebDriver driver = new RemoteWebDriver(new URL(seleniumServer), options);
        driver.manage().window().maximize();
        driver.navigate().to("http://google.com.vn");
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        Thread.sleep(1000);

        driver.quit();
        return "Success";
    }

    @GetMapping("/firefoxServer")
    public String firefoxServer() throws InterruptedException, MalformedURLException {
        System.out.println("Launching FIREFOX Server browser...: " + firefoxLocationUrl);
        System.setProperty("webdriver.gecko.driver", firefoxLocationUrl);
//        FirefoxOptions options = new FirefoxOptions();
//        WebDriver driver = new RemoteWebDriver(new URL(seleniumServer), options);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");

        // Khởi tạo RemoteWebDriver
        WebDriver driver = new RemoteWebDriver(new URL(seleniumServer), capabilities);
        driver.manage().window().maximize();
        driver.navigate().to("http://google.com.vn");
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        Thread.sleep(1000);

        driver.quit();
        return "Success";
    }

    @GetMapping("/firefox")
    public String firefox() throws InterruptedException, MalformedURLException {
        System.out.println("Launching FIREFOX browser...");
        System.setProperty("webdriver.gecko.driver", firefoxLocationUrl);
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to("http://google.com.vn");
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        Thread.sleep(1000);

        driver.quit();
        return "Success";
    }
}

