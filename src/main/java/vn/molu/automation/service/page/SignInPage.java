package vn.molu.automation.service.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import vn.molu.common.utils.DesEncrypterUtils;
import vn.molu.domain.admin.User;

public class SignInPage {
    private WebDriver driver;
    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean signin(WebDriver driver, String appUrl, By txtUsername, By txtPassword, By btnLogin, User user) throws InterruptedException {
        do {
            driver.get(appUrl);
        } while (!driver.findElement(txtUsername).isDisplayed());
        WebElement username = driver.findElement(txtUsername);
        username.sendKeys(user.getUserName());
        Thread.sleep(1000);
        WebElement password = driver.findElement(txtPassword);
        password.sendKeys(DesEncrypterUtils.getInstance().decrypt(user.getPassword()));
        Thread.sleep(1000);
        WebElement btnDangnhap = driver.findElement(btnLogin);
        btnDangnhap.click();
        Thread.sleep(1000);
        return true;
    }
}
