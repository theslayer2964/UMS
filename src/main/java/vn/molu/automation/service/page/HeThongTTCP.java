package vn.molu.automation.service.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import vn.molu.automation.service.basic.BasicSetup;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.domain.admin.User;
import vn.molu.dto.admin.admin.C2UserAdminDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Service
public class HeThongTTCP extends BasicSetup {
    private WebDriver driver;
    public SignInPage signInPage;
    private String chromeLocationUrl;
    private String seleniumServer;
    private static final String appUrl = "https://ttcp.mobifone.vn/b9_ttcp/";
    private By txtUsername_Login = By.xpath("//input[@id='frm_login:username']");
    private By txtPassword_Login = By.xpath("//input[@id='frm_login:password']");
    private By btnLogin = By.xpath("//span[@class='ui-button-text ui-c']");
    private By btnDanhmuc = By.xpath("(//span[@class='ui-button-text ui-c'][contains(text(),'Truy cập')])[6]");
    private By txtFullname = By.xpath("//input[@id='parentForm:txtStaffName']");
    private By tableActiveUserResult = By.xpath("//span[contains(text(), 'Hiệu lực')]");
    private By toThuCuoc = By.xpath("//input[@id='parentForm:chooseGroup_input']");
    private By danhMucNhanVien = By.xpath("//a[contains(text(),'Danh mục nhân viên')]");
    private By txt_searchUserRS = By.xpath("//span[@class='ui-paginator-current']");

    public void setUp() {
        driver = getDriver(appUrl, chromeLocationUrl, seleniumServer);
    }

    public boolean createUser(C2AdminUserAuto dto, User user) throws InterruptedException, ParseException {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            if (chonDanhmucNV()) {
                return themUser(dto);
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public HeThongTTCP() {
    }

    public HeThongTTCP(String chromeLocationUrl, String seleniumServer) {
        this.chromeLocationUrl = chromeLocationUrl;
        this.seleniumServer = seleniumServer;
    }

    public String getChromeLocationUrl() {
        return chromeLocationUrl;
    }

    public void setChromeLocationUrl(String chromeLocationUrl) {
        this.chromeLocationUrl = chromeLocationUrl;
    }

    public String getSeleniumServer() {
        return seleniumServer;
    }

    public void setSeleniumServer(String seleniumServer) {
        this.seleniumServer = seleniumServer;
    }

    public boolean themUser(C2AdminUserAuto dto) throws InterruptedException {
        System.out.println("b2: THEM:" + dto.toString());
        WebElement txtToThuCuoc = driver.findElement(toThuCuoc);
        txtToThuCuoc.clear();
        Thread.sleep(1000);
        txtToThuCuoc.sendKeys(dto.getShop_code());
        Thread.sleep(2000);
        List<WebElement> disableUserList = driver.findElements(By.xpath("//td[contains(text(), '" + dto.getShop_code() + "')]"));
        Thread.sleep(2000);
        if (disableUserList.size() != 0) { // có tổ thu đó
            Thread.sleep(1000);
            txtToThuCuoc.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,450)");
            Thread.sleep(2000);
            driver.findElement(By.xpath("//span[normalize-space()='Thêm']")).click(); // THEM
            Thread.sleep(1000);
            WebElement maNV = driver.findElement(By.xpath("//input[@id='parentForm:txtStaffCode']"));
            maNV.sendKeys(dto.getUser_name().trim().toString());
            Thread.sleep(1000);
            maNV.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            System.out.println("check user -mã hieu luc -> đã ton tai");
            if (driver.findElements(By.xpath("//span[contains(text(), 'đã tồn tại')]")).size() != 0) {
                Thread.sleep(4000);
                System.out.println("đã tồn tại " + maNV);
                return true;
            } else {
                System.out.println("chưa tồn tại " + maNV);
                WebElement fullnameNV = driver.findElement(txtFullname);
                fullnameNV.sendKeys(dto.getFull_name());
                jse.executeScript("window.scrollBy(0,450)");
                Thread.sleep(1000);
                try {
                    WebElement birthday = driver.findElement(By.xpath("//input[@id='parentForm:dtpBirthday_input']"));
                    Thread.sleep(1000);
                    birthday.click(); // SAI NE` -> BI BI BI
                    Thread.sleep(1000);
                    birthday.sendKeys("15032024");
                    Thread.sleep(1000);
                    jse.executeScript("window.scrollBy(0,450)");
                    WebElement cmnd = driver.findElement(By.xpath("//input[@id='parentForm:txtIdNo']"));
                    Thread.sleep(1000);
                    cmnd.sendKeys(dto.getCmnd());
                    Thread.sleep(1000);
                    jse.executeScript("window.scrollBy(0,450)");
                    WebElement noicap = driver.findElement(By.xpath("//input[@id='parentForm:txtIdIssuePlace']"));
                    Thread.sleep(1000);
                    noicap.sendKeys(dto.getNoicap());
                    Thread.sleep(1000);
                    jse.executeScript("window.scrollBy(0,450)");
                    WebElement ngaycap = driver.findElement(By.xpath("//input[@id='parentForm:dtpIdIssueDate_input']"));
                    Thread.sleep(1000);
                    ngaycap.click(); // SAI NE` -> BI BI BI
                    Thread.sleep(1000);
                    ngaycap.sendKeys("19032024");
                    Thread.sleep(1000);
                    jse.executeScript("window.scrollBy(0,450)");
                    WebElement trangthai = driver.findElement(By.xpath("/html[1]/body[1]/main[1]/section[1]/section[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[2]/fieldset[2]/div[1]/table[1]/tbody[1]/tr[3]/td[4]/div[1]"));
                    Thread.sleep(1000);
                    trangthai.click();
                    if (dto.getStatus() == 0) {
                        WebElement trangthai_KoHieuLuc = driver.findElement(By.xpath("//li[@id='parentForm:idStatus_1']"));
                        trangthai_KoHieuLuc.click();
                        Thread.sleep(1000);
                    } else {
                        WebElement trangthai_HieuLuc = driver.findElement(By.xpath("//li[@id='parentForm:idStatus_0']"));
                        trangthai_HieuLuc.click();
                        Thread.sleep(1000);
                    }
                    WebElement btnChapnhan = driver.findElement(By.xpath("//button[@id='parentForm:frmActionsTop:idAccept']"));
                    Thread.sleep(1000);
                    btnChapnhan.click(); // CLICK
                    Thread.sleep(5000);
                    driver.quit();
                    return true;
                } catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            System.out.println("KHONG CO TO THU DO");
            driver.quit();
            return false;
        }
    }

    private boolean chonDanhmucNV() throws InterruptedException {
        WebElement btnChonToThu = driver.findElement(By.xpath("//span[contains(text(),'Đồng ý')]"));
        btnChonToThu.click();
        Thread.sleep(1000);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,250)");
        Thread.sleep(3000);
        boolean flag = waitElementClick(driver);
        Thread.sleep(2000);
        if (flag == true) {
            try {
                if (driver.findElements(danhMucNhanVien).size() == 0) {
                    Thread.sleep(3000);
                    WebElement litag_DM = driver.findElement(By.xpath("(//a[@href='#'][contains(text(),'Danh mục')])[1]"));
                    Thread.sleep(1000);
                    litag_DM.click();
                    Thread.sleep(1000);
                }
                WebElement litag_DMNhanvien = driver.findElement(danhMucNhanVien);
                litag_DMNhanvien.click();
                Thread.sleep(2000);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private boolean waitElementClick(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            WebElement btnDanhMucWait = wait.until(ExpectedConditions.visibilityOfElementLocated(btnDanhmuc));
            btnDanhMucWait.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean changeShopcode(C2UserAdminDTO dto, String shopcode_old, User user) {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            if (chonDanhmucNV()) {
                boolean step1Success = disableUserOldShop(dto, shopcode_old, "0");
                System.out.println("THEM USER DO SHOP MOI");
                return step1Success ? themUser(convertUser(dto)) : false;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inactiveUser(C2UserAdminDTO dto, String shopcode_old, User user) {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            return chonDanhmucNV() ? disableUserOldShopThenQuit(dto, shopcode_old, "0") : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private C2AdminUserAuto convertUser(C2UserAdminDTO dto) throws InterruptedException {
        LocalDate calendar = LocalDate.now();
        Thread.sleep(1000);
        String birth = calendar.minusDays(10).toString();
        Thread.sleep(1000);
        String ngaycap = calendar.minusDays(5).toString();
        Thread.sleep(1000);
        C2AdminUserAuto c2AdminUserAuto = new C2AdminUserAuto();
        Thread.sleep(1000);
        c2AdminUserAuto.setShop_code(dto.getShopCode());
        Thread.sleep(1000);
        c2AdminUserAuto.setUser_name(dto.getUsername());
        Thread.sleep(1000);
        c2AdminUserAuto.setFull_name(dto.getFullName());
        Thread.sleep(1000);
        c2AdminUserAuto.setBirthday(birth);
        Thread.sleep(1000);
        c2AdminUserAuto.setCmnd("1");
        Thread.sleep(1000);
        c2AdminUserAuto.setNoicap("1");
        Thread.sleep(1000);
        c2AdminUserAuto.setNgaycap(ngaycap);
        Thread.sleep(1000);
        if (dto.getStatus() != null) {
            c2AdminUserAuto.setStatus(Integer.parseInt(dto.getStatus()));
        } else c2AdminUserAuto.setStatus(1);
        Thread.sleep(1000);
        return c2AdminUserAuto;
    }

    private boolean disableUserOldShop(C2UserAdminDTO dto, String shopcodeOld, String status) throws InterruptedException {
        System.out.println("XDISABLEX:" + dto.toString() + "SHOP CU:" + shopcodeOld);
        WebElement txtToThuCuoc = driver.findElement(toThuCuoc);
        txtToThuCuoc.clear();
        Thread.sleep(1000);
        txtToThuCuoc.sendKeys(shopcodeOld);
        Thread.sleep(2000);
        List<WebElement> shopcodeRS = driver.findElements(By.xpath("//td[contains(text(), '" + shopcodeOld + "')]"));
        Thread.sleep(3000);
        if (shopcodeRS.size() != 0) { // co old shop code
            System.out.println("CO OLD SHOP");
            txtToThuCuoc.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            txtToThuCuoc.click();
            Thread.sleep(1000);
            WebElement txtUsername = driver.findElement(By.xpath("//input[@id='parentForm:tblStaff:j_idt121:filter']"));
            Thread.sleep(1000);
            txtUsername.sendKeys(dto.getUsername());
            Thread.sleep(1000);
            txtUsername.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            try {
                String searchUserRS = driver.findElement(txt_searchUserRS).getText().split(" ")[2];
                if (Integer.parseInt(searchUserRS.trim()) > 0) { // co user
                    List<WebElement> user_hieuluc = driver.findElements(By.xpath("//span[text()='Hiệu lực']"));
                        Thread.sleep(5000);
                    if(user_hieuluc.size() > 0){ // user đó co hieu luc
                        driver.findElement(By.xpath("//span[text()='Hiệu lực']")).click();
                        Thread.sleep(1000);
                        ((JavascriptExecutor) driver)
                                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
                        Thread.sleep(1000);
                        // btnSua- -> SAI NE```
                        WebElement btnSua = driver.findElement(By.xpath("//button[@id='parentForm:frmActionsTop:idUpdate']"));
                        Thread.sleep(1000);
                        btnSua.click();
                        Thread.sleep(1000);
                        WebElement trangthai = driver.findElement(By.xpath("/html[1]/body[1]/main[1]/section[1]/section[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[2]/fieldset[2]/div[1]/table[1]/tbody[1]/tr[3]/td[4]/div[1]"));
                        trangthai.click();
                        Thread.sleep(1000);
                        driver.findElement(By.xpath("//li[@id='parentForm:idStatus_1']")).click(); // trangthai_KoHieuLuc
                        Thread.sleep(1000);
                        WebElement btnSave = driver.findElement(By.xpath("//button[@id='parentForm:frmActionsTop:idAccept']//span[@class='ui-button-text ui-c'][contains(text(),'Chấp nhận')]"));
                        Thread.sleep(1000);
                        btnSave.click(); // SAVE
                        Thread.sleep(1000);
                        if (driver.findElements(By.xpath("//span[contains(text(), 'Dữ liệu được cập nhật thành công.')]")).size() != 0) {
                            driver.findElement(By.xpath("//div[@id='commonDialog']//span[@class='ui-icon ui-icon-closethick']")).click();
                            Thread.sleep(1000); // close table
                            return true;
                        } else {
                            System.out.println("USER NAY` Vẫn còn hiệu luc ở đâu đó - ko disable đc");
                            Thread.sleep(4000);
                            return false;
                        }
                    } else { // co user-> nhung user ddos het hieu luc -> qua ok -> khoi sua
                        return true;
                    }

                } else {
                    System.out.println("ko co user trong shop cu");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else { // ko co old shop code
            System.out.println("KO CO OLD SHOP");
            return true;
        }
    }

    private boolean disableUserOldShopThenQuit(C2UserAdminDTO dto, String shopcodeOld, String status) throws InterruptedException {
        System.out.println("XDISABLEX:" + dto.toString() + "SHOP CU:" + shopcodeOld);
        WebElement txtToThuCuoc = driver.findElement(toThuCuoc);
        txtToThuCuoc.clear();
        Thread.sleep(1000);
        txtToThuCuoc.sendKeys(shopcodeOld);
        Thread.sleep(2000);
        List<WebElement> shopcodeRS = driver.findElements(By.xpath("//td[contains(text(), '" + shopcodeOld + "')]"));
        Thread.sleep(3000);
        if (shopcodeRS.size() != 0) { // co old shop code
            System.out.println("CO OLD SHOP");
            txtToThuCuoc.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            txtToThuCuoc.click();
            Thread.sleep(1000);
            WebElement txtUsername = driver.findElement(By.xpath("//input[@id='parentForm:tblStaff:j_idt121:filter']"));
            Thread.sleep(1000);
            txtUsername.sendKeys(dto.getUsername());
            Thread.sleep(1000);
            txtUsername.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            try {
                String searchUserRS = driver.findElement(txt_searchUserRS).getText().split(" ")[2];
                if (Integer.parseInt(searchUserRS.trim()) > 0) { // co user
                    driver.findElement(By.xpath("//span[text()='Hiệu lực']")).click();
                    Thread.sleep(1000);
                    ((JavascriptExecutor) driver)
                            .executeScript("window.scrollTo(0, document.body.scrollHeight)");
                    Thread.sleep(1000);
                    // btnSua- -> SAI NE```
                    WebElement btnSua = driver.findElement(By.xpath("//button[@id='parentForm:frmActionsTop:idUpdate']"));
                    Thread.sleep(1000);
                    btnSua.click();
                    Thread.sleep(1000);
                    WebElement trangthai = driver.findElement(By.xpath("/html[1]/body[1]/main[1]/section[1]/section[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[2]/fieldset[2]/div[1]/table[1]/tbody[1]/tr[3]/td[4]/div[1]"));
                    trangthai.click();
                    Thread.sleep(1000);
                    WebElement trangthai_KoHieuLuc = driver.findElement(By.xpath("//li[@id='parentForm:idStatus_1']"));
                    trangthai_KoHieuLuc.click();
                    Thread.sleep(1000);
                    WebElement btnChapnhan = driver.findElement(By.xpath("//button[@id='parentForm:frmActionsTop:idAccept']//span[@class='ui-button-text ui-c'][contains(text(),'Chấp nhận')]"));
                    btnChapnhan.click(); // CLICK
                    Thread.sleep(1000);
                    if (driver.findElements(By.xpath("//span[contains(text(), 'Dữ liệu được cập nhật thành công.')]")).size() != 0) {
                        driver.findElement(By.xpath("//div[@id='commonDialog']//span[@class='ui-icon ui-icon-closethick']")).click();
                        Thread.sleep(1000); // close table
                        driver.quit();
                        return true;
                    } else {
                        System.out.println("USER NAY` Vẫn còn hiệu luc ở đâu đó - ko disable đc");
                        Thread.sleep(4000);
                        driver.quit();
                        return false;
                    }
                } else {
                    System.out.println("ko co user - shop cu");
                    driver.quit();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                driver.quit();
                return false;
            }
        } else { // ko co old shop code
            System.out.println("KO CO OLD SHOP");
            driver.quit();
            return false;
        }
    }
}
