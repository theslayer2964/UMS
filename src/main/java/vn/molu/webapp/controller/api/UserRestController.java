package vn.molu.webapp.controller.api;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@RestController
public class UserRestController {
    @Value("${system.chrome.path}")
    private String chromeLocationUrl;

    @GetMapping("/api/user.html")
    public String list() {
        System.out.println("======================");
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/example")
    public String example() throws InterruptedException, MalformedURLException {
        System.out.println("Launching Chrome browser..."+ chromeLocationUrl);
        String seleniumHubUrl = "http://10.39.68.174:4444/wd/hub";
        ChromeOptions options = new ChromeOptions();

//        System.setProperty("webdriver.chrome.driver", chromeLocationUrl);
        WebDriver driver = new RemoteWebDriver(new URL(seleniumHubUrl), options);
        driver.manage().window().maximize();
        driver.navigate().to("http://google.com.vn");
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        Thread.sleep(1000);

        driver.quit();
        return "Success";
    }
}

