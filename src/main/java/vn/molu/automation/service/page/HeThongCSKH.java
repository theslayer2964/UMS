package vn.molu.automation.service.page;

import org.openqa.selenium.*;
import org.springframework.stereotype.Service;
import vn.molu.automation.service.basic.BasicSetup;
import vn.molu.common.Constants;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.domain.admin.User;
import vn.molu.dto.admin.admin.C2UserAdminDTO;

import java.text.ParseException;

@Service
public class HeThongCSKH extends BasicSetup {
    private WebDriver driver;
    public SignInPage signInPage;
    private String chromeLocationUrl;
    private String seleniumServer;
    private static final String appUrl = "https://daunoi.mobifone.vn/b9_cskh/login.xhtml";
    private By txtUsername_Login = By.xpath("//input[@id='frm_login:username']");
    private By txtPassword_Login = By.xpath("//input[@id='frm_login:password']");
    private By btnLogin = By.xpath("//span[contains(text(),'Đăng nhập')]");
    private By btnChonDanhmuc = By.xpath("(//span[@class='ui-button-text ui-c'][contains(text(),'Truy cập')])[6]");
    private By cbbQuanhuyen = By.xpath("//input[@id='frmDialog:district_input']");
    private By liDM_NV = By.xpath("//a[contains(text(),'DM nhân viên')]");
    private By cbbStatusUser = By.xpath("//div[@id='frmDialog:j_idt189']//span[@class='ui-icon ui-icon-triangle-1-s ui-c']");
    private By txtUsernameChangeShop = By.xpath("//input[@id='frmDialog:username']");
    private By searchUserRS = By.xpath("//span[contains(text(),'bản ghi')]");

    public void setUp() {
        driver = getDriver(appUrl, chromeLocationUrl, seleniumServer);
    }

    public String getSeleniumServer() {
        return seleniumServer;
    }

    public void setSeleniumServer(String seleniumServer) {
        this.seleniumServer = seleniumServer;
    }

    public HeThongCSKH(String chromeLocationUrl, String seleniumServer) {
        this.chromeLocationUrl = chromeLocationUrl;
        this.seleniumServer = seleniumServer;
    }

    public HeThongCSKH() {
    }

    public String getChromeLocationUrl() {
        return chromeLocationUrl;
    }

    public void setChromeLocationUrl(String chromeLocationUrl) {
        this.chromeLocationUrl = chromeLocationUrl;
    }

    public boolean createUser(C2AdminUserAuto dto, String city_name, User user) throws InterruptedException, ParseException {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            return clickNhanvienPhattrienDV() ? themUser(dto, city_name) : false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean themUser(C2AdminUserAuto dto, String city_name) {
        try {
            System.out.println("XXX:" + dto.toString());
            //click
            WebElement txtSearch = driver.findElement(By.xpath("//input[@id='j_idt109:search']"));
            txtSearch.clear();
            Thread.sleep(1000);
            txtSearch.sendKeys(dto.getShop_code());
            Thread.sleep(1000);
            driver.findElement(By.xpath("//button[@id='j_idt109:j_idt116']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[contains(text(), '" + dto.getShop_code() + " -')]")).click();
            Thread.sleep(1000); //
            driver.findElement(By.xpath("//span[contains(text(),'Thêm mới')]")).click();
            Thread.sleep(1000);
            //them TT:
            WebElement txtUser = driver.findElement(By.xpath("//input[@id='frmDialog:empCode']"));
            while (txtUser.getAttribute("value").equals("")) {
                System.out.println("txtUser");
                txtUser.sendKeys(dto.getUser_name());
            }
            Thread.sleep(1000);
            if (dto.getStatus() == 0) {
                WebElement cbbStatus = driver.findElement(By.xpath("//label[@id='frmDialog:j_idt189_label']"));
                cbbStatus.click();
                Thread.sleep(1000);
                WebElement cbbStatus_Passive = driver.findElement(By.xpath("//li[@id='frmDialog:j_idt189_0']"));
                cbbStatus_Passive.click();
                Thread.sleep(1000);
            }
            WebElement txtFullname = driver.findElement(By.xpath("//input[@id='frmDialog:j_idt196']"));
            txtFullname.sendKeys(dto.getFull_name());
            Thread.sleep(1000);
            WebElement cbbLoaiNV = driver.findElement(By.xpath("//input[@id='frmDialog:empTypeAuCom_input']"));
            cbbLoaiNV.clear();
            Thread.sleep(1000);
            cbbLoaiNV.sendKeys(String.valueOf(dto.getType()));
            Thread.sleep(1000);
            cbbLoaiNV.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            WebElement cbbCity = driver.findElement(By.xpath("//input[@id='frmDialog:province_input']"));
            cbbCity.sendKeys(city_name);
            Thread.sleep(1000);
            cbbCity.sendKeys(Keys.ENTER);
            Thread.sleep(1000);

            WebElement cbbDistrict = driver.findElement(cbbQuanhuyen);
            String convertedDistrict = convertDistrict(dto.getDistrict()).toLowerCase();
            do {
                cbbDistrict.sendKeys(convertedDistrict);
                Thread.sleep(2000);
                cbbDistrict.sendKeys(Keys.ENTER);
                Thread.sleep(1000);
            } while (driver.findElement(cbbQuanhuyen).getAttribute("value").trim().equals(""));
            WebElement txtPhone = driver.findElement(By.xpath("//input[@id='frmDialog:j_idt265']"));
            txtPhone.sendKeys(dto.getPhone());
            Thread.sleep(1000);
            WebElement usernameField = driver.findElement(txtUsernameChangeShop);
            Thread.sleep(1000);
            usernameField.clear();
            Thread.sleep(1000);
            usernameField.sendKeys(dto.getUser_name());
            // CLICK
            driver.findElement(By.xpath("//button[@id='frmDialog:j_idt270']//span[@class='ui-button-text ui-c'][contains(text(),'Lưu dữ liệu')]")).click();
            Thread.sleep(5000);
            driver.quit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String convertDistrict(String district) {
        if (district.equals("Quận 1")) {
            return "Quận 01";
        } else if (district.equals("Quận 2")) {
            return "Quận 02";
        } else if (district.equals("Quận 3")) {
            return "Quận 03";
        } else if (district.equals("Quận 4")) {
            return "Quận 04";
        } else if (district.equals("Quận 5")) {
            return "Quận 05";
        } else if (district.equals("Quận 6")) {
            return "Quận 06";
        } else if (district.equals("Quận 7")) {
            return "Quận 07";
        } else if (district.equals("Quận 8")) {
            return "Quận 08";
        } else if (district.equals("Quận 9")) {
            return "Quận 09";
        } else return district;
    }

    private boolean clickNhanvienPhattrienDV() throws InterruptedException {
        boolean flag = false;
        while (flag == false) {
            driver.navigate().to("https://daunoi.mobifone.vn/b9_cskh/admin-home.xhtml");
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,250)");
            Thread.sleep(1000);
            flag = waitEmlement(driver, 2000, "(//span[@class='ui-button-text ui-c'][contains(text(),'Truy cập')])[6]");
        }
        if (flag == true) {
            driver.findElement(btnChonDanhmuc).click();
            Thread.sleep(2000);
            WebElement liTagDmNhanVien = driver.findElement(liDM_NV);
            liTagDmNhanVien.click();
            Thread.sleep(2000);
            WebElement liTabgNV_PhattrienDV = driver.findElement(By.xpath("//a[contains(text(),'Nhân viên phát triển DV')]"));
            liTabgNV_PhattrienDV.click();
            Thread.sleep(2000);
        }
        return true;
    }

//    private boolean waitElementClick(WebDriver driver) {
//        WebDriverWait wait = new WebDriverWait(driver, 3);
//        WebElement btnDanhMucWait = wait.until(ExpectedConditions.visibilityOfElementLocated(btnChonDanhmuc));
//        btnDanhMucWait.click();
//        return true;
//    }

    public static boolean waitEmlement(WebDriver driver, int timeout, String path) {
        boolean flag = false;
        int counter = 0;
        while (!flag) {
            try {
                if (driver.findElement(By.xpath(path)).isDisplayed()) {
                    flag = true;
                    return flag;
                }
                Thread.sleep(500);
                counter++;
                if (counter > timeout) {
                    return flag;
                }
            } catch (Exception e) {
//                e.printStackTrace();
                return false;
            }
        }
        return flag;
    }

    public boolean changeShopcode(C2UserAdminDTO dto, String shopcode_old, User user) throws InterruptedException {
        try {
            System.out.println("CSKH:" + dto);
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            if (clickNhanvienPhattrienDV()) {
                return disableUserInOldShop(dto, shopcode_old) ?
                        themUser(convertUser(dto), Constants.CITY_DEFAULT) : false;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean inactiveUser(C2UserAdminDTO dto, String shopcode_old, User user) {
        try {
            System.out.println("CSKH:" + dto);
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            return clickNhanvienPhattrienDV() ?
                disableUserInOldShopThenQuit(dto, shopcode_old) : false;
        } catch (Exception e) {
            return false;
        }
    }

    private C2AdminUserAuto convertUser(C2UserAdminDTO dto) {
        C2AdminUserAuto c2AdminUserAuto = new C2AdminUserAuto();
        c2AdminUserAuto.setShop_code(dto.getShopCode());
        c2AdminUserAuto.setUser_name(dto.getUsername());
        c2AdminUserAuto.setStatus(Integer.parseInt(dto.getStatus()));
        c2AdminUserAuto.setFull_name(dto.getFullName());
        c2AdminUserAuto.setType(Integer.parseInt(dto.getAccount()));
        c2AdminUserAuto.setDistrict(Constants.DISTRICT_DEFAULT);
        c2AdminUserAuto.setPhone("1");
        return c2AdminUserAuto;
    }

    private boolean disableUserInOldShop(C2UserAdminDTO dto, String shopcodeOld) throws InterruptedException {
        System.out.println("CHANGE SHOP - CSKH:" + dto.toString());
        // select shop
        WebElement txtSearch = driver.findElement(By.xpath("//input[@id='j_idt109:search']"));
        txtSearch.sendKeys(shopcodeOld);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@id='j_idt109:j_idt116']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(), '" + shopcodeOld + " -')]")).click();
        Thread.sleep(1000);
        // select user:
        WebElement txtUserSearch = driver.findElement(By.xpath("//input[@id='frmEmpDetail:tblData:j_idt139:filter']"));
        txtUserSearch.sendKeys(dto.getUsername());
        Thread.sleep(1000);
        txtUserSearch.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        // change pointer
        txtSearch.clear();
        Thread.sleep(2000);
        txtSearch.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        String searchUserRS_number = driver.findElement(searchUserRS).getText().split(" ")[2];
        Thread.sleep(1000);
        System.out.println(Integer.parseInt(searchUserRS_number));
        try {
            if (Integer.parseInt(searchUserRS_number) > 0) {
                System.out.println("co user");
                //btnEdit
                driver.findElement(By.xpath("//a[@id='frmEmpDetail:tblData:0:j_idt136']")).click();
                Thread.sleep(1000);
                driver.findElement(cbbStatusUser).click(); // disable user
                Thread.sleep(1000);
                WebElement cbbStatus_Passive = driver.findElement(By.xpath("//li[@id='frmDialog:j_idt189_0']"));
                cbbStatus_Passive.click();
                Thread.sleep(1000);
                WebElement txtFullname = driver.findElement(By.xpath("//input[@id='frmDialog:j_idt196']"));
                if (txtFullname.getAttribute("value").equals("")) {
                    Thread.sleep(1000);
                    txtFullname.sendKeys(dto.getFullName());
                    Thread.sleep(1000);
                }
                WebElement txtStartDate = driver.findElement(By.xpath("//input[@id='frmDialog:staDate_input']"));
                if (txtStartDate.getAttribute("value").equals("")) {
                    Thread.sleep(1000);
                    txtStartDate.sendKeys("01032024");
                    Thread.sleep(1000);
                }
                WebElement cbbLoaiNV = driver.findElement(By.xpath("//input[@id='frmDialog:empTypeAuCom_input']"));
                if (cbbLoaiNV.getAttribute("value").equals("")) {
                    cbbLoaiNV.sendKeys("1");
                    Thread.sleep(1000);
                    cbbLoaiNV.sendKeys(Keys.ENTER);
                    Thread.sleep(1000);
                }
                WebElement cbbCity = driver.findElement(By.xpath("//input[@id='frmDialog:province_input']"));
                if (cbbCity.getAttribute("value").equals("")) {
                    cbbCity.sendKeys(Constants.CITY_DEFAULT);
                    Thread.sleep(1000);
                    cbbCity.sendKeys(Keys.ENTER);
                    Thread.sleep(1000);
                }
                WebElement cbbDistrict = driver.findElement(cbbQuanhuyen);
                if (cbbDistrict.getAttribute("value").equals("")) {
                    cbbDistrict.sendKeys(Constants.DISTRICT_DEFAULT);
                    Thread.sleep(2000);
                    cbbDistrict.sendKeys(Keys.ENTER);
                    Thread.sleep(1000);
                }
                WebElement txtPhone = driver.findElement(By.xpath("//input[@id='frmDialog:j_idt265']"));
                Thread.sleep(1000);
                if (txtPhone.getText().trim().equals("")) {
                    Thread.sleep(1000);
                    txtPhone.sendKeys("1");
                }
                // save
                driver.findElement(By.xpath("//button[@id='frmDialog:j_idt270']//span[@class='ui-button-text ui-c'][contains(text(),'Lưu dữ liệu')]")).click();
                Thread.sleep(1000);
                // close popup table
                driver.findElement(By.xpath("//div[@id='custcareMessageId']//a[@role='button']")).click();
                return true;
            } else {
                System.out.println("ko co user do");
                return true;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }

    private boolean disableUserInOldShopThenQuit(C2UserAdminDTO dto, String shopcodeOld) throws InterruptedException {
        System.out.println("CHANGE SHOP - CSKH:" + dto.toString());
        // select shop
        WebElement txtSearch = driver.findElement(By.xpath("//input[@id='j_idt109:search']"));
        txtSearch.sendKeys(shopcodeOld);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@id='j_idt109:j_idt116']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(), '" + shopcodeOld + " -')]")).click();
        Thread.sleep(1000);
        // select user:
        WebElement txtUserSearch = driver.findElement(By.xpath("//input[@id='frmEmpDetail:tblData:j_idt139:filter']"));
        txtUserSearch.sendKeys(dto.getUsername());
        Thread.sleep(1000);
        txtUserSearch.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        // change pointer
        txtSearch.clear();
        Thread.sleep(2000);
        txtSearch.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        String searchUserRS_number = driver.findElement(searchUserRS).getText().split(" ")[2];
        Thread.sleep(1000);
        System.out.println(Integer.parseInt(searchUserRS_number));
        try {
            if (Integer.parseInt(searchUserRS_number) > 0) {
                System.out.println("co user");
                //btnEdit
                driver.findElement(By.xpath("//a[@id='frmEmpDetail:tblData:0:j_idt136']")).click();
                Thread.sleep(1000);
                driver.findElement(cbbStatusUser).click(); // disable user
                Thread.sleep(1000);
                WebElement cbbStatus_Passive = driver.findElement(By.xpath("//li[@id='frmDialog:j_idt189_0']"));
                cbbStatus_Passive.click();
                Thread.sleep(1000);
                WebElement txtFullname = driver.findElement(By.xpath("//input[@id='frmDialog:j_idt196']"));
                if (txtFullname.getAttribute("value").equals("")) {
                    Thread.sleep(1000);
                    txtFullname.sendKeys(dto.getFullName());
                    Thread.sleep(1000);
                }
                WebElement txtStartDate = driver.findElement(By.xpath("//input[@id='frmDialog:staDate_input']"));
                if (txtStartDate.getAttribute("value").equals("")) {
                    Thread.sleep(1000);
                    txtStartDate.sendKeys("01032024");
                    Thread.sleep(1000);
                }
                WebElement cbbLoaiNV = driver.findElement(By.xpath("//input[@id='frmDialog:empTypeAuCom_input']"));
                if (cbbLoaiNV.getAttribute("value").equals("")) {
                    cbbLoaiNV.sendKeys("1");
                    Thread.sleep(1000);
                    cbbLoaiNV.sendKeys(Keys.ENTER);
                    Thread.sleep(1000);
                }
                WebElement cbbCity = driver.findElement(By.xpath("//input[@id='frmDialog:province_input']"));
                if (cbbCity.getAttribute("value").equals("")) {
                    cbbCity.sendKeys(Constants.CITY_DEFAULT);
                    Thread.sleep(1000);
                    cbbCity.sendKeys(Keys.ENTER);
                    Thread.sleep(1000);
                }
                WebElement cbbDistrict = driver.findElement(cbbQuanhuyen);
                if (cbbDistrict.getAttribute("value").equals("")) {
                    cbbDistrict.sendKeys(Constants.DISTRICT_DEFAULT);
                    Thread.sleep(2000);
                    cbbDistrict.sendKeys(Keys.ENTER);
                    Thread.sleep(1000);
                }
                WebElement txtPhone = driver.findElement(By.xpath("//input[@id='frmDialog:j_idt265']"));
                Thread.sleep(1000);
                if (txtPhone.getText().trim().equals("")) {
                    Thread.sleep(1000);
                    txtPhone.sendKeys("1");
                }
                // save
                driver.findElement(By.xpath("//button[@id='frmDialog:j_idt270']//span[@class='ui-button-text ui-c'][contains(text(),'Lưu dữ liệu')]")).click();
                Thread.sleep(1000);
                // close popup table
                driver.findElement(By.xpath("//div[@id='custcareMessageId']//a[@role='button']")).click();
                driver.quit();
                return true;
            } else {
                System.out.println("ko co user do");
                driver.quit();
                return false;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            driver.quit();
            return false;
        }
    }
}
