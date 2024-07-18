package vn.molu.webapp.controller.api;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
    private String seleniumHub;
    @Value("${selenium.gecko-driver.path}")
    private String firefoxLocationUrl;

    @GetMapping("/api/user.html")
    public String list() {
        System.out.println("======================");
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/example")
    public String example() throws InterruptedException, MalformedURLException {
        try {
            System.out.println("Launching Chrome browser...: " + seleniumHub);
            // url
            DesiredCapabilities caps = DesiredCapabilities.firefox();
            caps.setBrowserName("firefox");
            caps.setPlatform(Platform.ANY);
            WebDriver driver = new RemoteWebDriver(new URL(seleniumHub), caps);
            driver.manage().window().maximize();
            driver.navigate().to("http://google.com.vn");
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            Thread.sleep(1000);

            driver.quit();
            return "Success";
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/local")
    public String local() throws InterruptedException, MalformedURLException {
        try {
            System.out.println("Launching Chrome - local - browser...: " + firefoxLocationUrl);
            ///
            System.setProperty("webdriver.gecko.driver", firefoxLocationUrl);
            WebDriver driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.navigate().to("http://google.com.vn");
            System.out.println("Title: " + driver.getTitle());
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            Thread.sleep(5000);

            driver.quit();
            return "Success";
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

