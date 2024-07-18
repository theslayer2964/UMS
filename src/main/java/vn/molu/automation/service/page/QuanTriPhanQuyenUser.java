package vn.molu.automation.service.page;

import org.openqa.selenium.*;
import vn.molu.automation.service.basic.BasicSetup;
import vn.molu.common.Constants;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.domain.admin.GroupUserPermission;
import vn.molu.domain.admin.User;
import vn.molu.dto.admin.admin.C2UserAdminDTO;

import java.util.List;

public class QuanTriPhanQuyenUser extends BasicSetup {
    private WebDriver driver;
    public SignInPage signInPage;
    private String chromeLocationUrl;
    private String seleniumServer;
    private static final String appUrl = "http://dthgd.mobifone.vn:8005/admin/login";
    private By txtUsername_Login = By.xpath("//input[@placeholder='Tài khoản']");
    private By txtPassword_Login = By.xpath("//input[@placeholder='Mật khẩu']");
    private By btnLogin = By.xpath("//button[contains(text(),'Đăng Nhập')]");
    private By txtPhone = By.xpath("//input[@id='telNumber']");
    private By txtGrantedIp = By.xpath("//input[@id='grantedIp']");
    private By cbb_BatBuocDoiMatKhau = By.xpath("//body/div/div/div[@role='dialog']/div[@role='document']/div[@class='modal-content']/div[@id='dialog']/div[@class='modal-body']/div[@class='modal-body']/form[@id='mainForm']/div[@class='row']/div[@class='col-md-6']/div[2]/div[1]/label[1]/span[1]/input[1]");

    public void setUp() {
        driver = getDriver(appUrl, chromeLocationUrl, seleniumServer);
    }

    public String getSeleniumServer() {
        return seleniumServer;
    }

    public void setSeleniumServer(String seleniumServer) {
        this.seleniumServer = seleniumServer;
    }

    public boolean createUser(C2AdminUserAuto automationUser, List<GroupUserPermission> listGroupUserPermission, User user) throws InterruptedException {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            return chonDSNguoiDung() ? themmoiUser(automationUser, listGroupUserPermission) : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public QuanTriPhanQuyenUser(String chromeLocationUrl, String seleniumServer) {
        this.chromeLocationUrl = chromeLocationUrl;
        this.seleniumServer = seleniumServer;
    }

    public QuanTriPhanQuyenUser() {
    }

    public String getChromeLocationUrl() {
        return chromeLocationUrl;
    }

    public void setChromeLocationUrl(String chromeLocationUrl) {
        this.chromeLocationUrl = chromeLocationUrl;
    }

    private boolean themmoiUser(C2AdminUserAuto automationUser, List<GroupUserPermission> listGroupUserPermission) {
        try {
            System.out.println("XXX:" + automationUser.toString());
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,250)");
            Thread.sleep(2000);
            WebElement btnThemmoi = driver.findElement(By.xpath("//button[@type='button'][contains(text(),'Thêm mới')]"));
            jse.executeScript("window.scrollBy(0,250)");
            btnThemmoi.click();
            Thread.sleep(2000);
            WebElement inputFullname = driver.findElement(By.xpath("//input[@id='fullName']"));
            inputFullname.sendKeys(automationUser.getFull_name());
            Thread.sleep(1000);
            WebElement inputUsername = driver.findElement(By.xpath("//input[@id='userName']"));
            inputUsername.sendKeys(automationUser.getUser_name());
            Thread.sleep(1000);
            WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
            password.sendKeys(automationUser.getPassword());
            Thread.sleep(1000);
            WebElement repassword = driver.findElement(By.xpath("//input[@id='rePassword']"));
            repassword.sendKeys(automationUser.getPassword());
            Thread.sleep(1000);
            WebElement telephone = driver.findElement(By.xpath("//input[@id='telNumber']"));
            telephone.sendKeys(automationUser.getPhone());
            Thread.sleep(1000);
            // checkbox - bat buoc doi mat khau -> CHUA XONG
//            WebElement check_BatBuocDoiMK = driver.findElement(By.xpath("(//*[@class='ant-checkbox'])[4]"));
//            System.out.println("X54235XX:" + check_BatBuocDoiMK.getAttribute("class"));
//            if (!check_BatBuocDoiMK.getAttribute("class").contains("ant-checkbox-checked")) {
//                driver.findElement(cbb_BatBuocDoiMatKhau).click(); // checkbox - bat buoc doi mat khau
//            }
            // connect ldap
            if (automationUser.getLdap() != null && automationUser.getLdap().trim().equals("1")) {
                driver.findElement(By.xpath("//div//div[3]//div[1]//label[1]//span[1]//input[1]")).click();
                // ldap user:
                WebElement txt_account = driver.findElement(By.xpath("//input[@id='account']"));
                Thread.sleep(1000);
                txt_account.sendKeys(automationUser.getUser_name());
                Thread.sleep(1000);
            }
            WebElement btnShop_code = driver.findElement(By.xpath("(//*[@data-icon='down'])[8]"));
            btnShop_code.click();
            WebElement txtShop_code = driver.findElement(By.xpath("//div[@class='col-md-6']//div[1]//div[1]//div[1]//div[1]//div[2]//div[1]//input[1]"));
            txtShop_code.sendKeys(automationUser.getShop_code());
            Thread.sleep(1000);
            txtShop_code.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(1000);
            txtShop_code.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            WebElement btnToThu = driver.findElement(By.xpath("//body/div/div/div[@role='dialog']/div[@role='document']/div[@class='modal-content']/div[@id='dialog']/div[@class='modal-body']/div[@class='modal-body']/form[@id='mainForm']/div[@class='row']/div[1]/div[4]/div[2]/div[1]/div[1]/span[1]"));
            btnToThu.click();
            WebElement txtTothu = driver.findElement(By.xpath("(//input[@class='ant-select-search__field'])[1]"));
            txtTothu.sendKeys(automationUser.getShop_code());
            Thread.sleep(1000);
            txtTothu.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(1000);
            txtTothu.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            WebElement grantedIp = driver.findElement(txtGrantedIp);
            Thread.sleep(1000);
            grantedIp.sendKeys(automationUser.getGranted_ip());
            Thread.sleep(1000);
            WebElement lichTruycap = driver.findElement(By.xpath("//div[@id='calendarAccess']//div[@role='combobox']"));
            lichTruycap.click();
            Thread.sleep(1000);
            WebElement c1_cuahang = driver.findElement(By.xpath("//li[normalize-space()='" + automationUser.getAccess_schedule() + "']"));
            c1_cuahang.click();
            jse.executeScript("window.scrollBy(0,250)");
            Thread.sleep(1000);
            if (phanquyenUser(listGroupUserPermission)) {
                //CLICK -> LUU DATA
                driver.findElement(By.xpath("//button[contains(text(),'Lưu thông tin')]")).click();
                Thread.sleep(5000);
                driver.quit();
                return true;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean phanquyenUser(List<GroupUserPermission> listGroupUserPermission) {
        try {
            if (!listGroupUserPermission.isEmpty()) {
                driver.findElement(By.xpath("//div[@class='ant-select ant-select-enabled']")).click();
                Thread.sleep(1000);
                WebElement txtGroupUser = driver.findElement(By.xpath("//li[@class='ant-select-search ant-select-search--inline']//input[@class='ant-select-search__field']"));
                Thread.sleep(1000);
                listGroupUserPermission.forEach(permission -> {
                    try {
                        txtGroupUser.sendKeys(permission.getPermission());
                        Thread.sleep(1000);
                        driver.findElement(By.cssSelector("li[label*='" + permission.getPermission() + "']")).click();
                        Thread.sleep(1000);
                        txtGroupUser.sendKeys(Keys.ENTER);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                driver.findElement(By.xpath("//input[@id='fullName']")).click(); // nút thêm bị che -> làm nó hết che
                Thread.sleep(1000);
                driver.findElement(By.xpath("//section[@class='code-box expand']//div//button[@type='button'][normalize-space()='Thêm']")).click();
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean chonDSNguoiDung() {
        try {
            WebElement litab_ChucnangAdmin = driver.findElement(By.xpath("//a[contains(text(),'Chức năng Admin')]"));
            Thread.sleep(1000);
            litab_ChucnangAdmin.click();
            WebElement litab_DSNguoidung = driver.findElement(By.xpath("//a[contains(text(),'Danh sách người dùng')]"));
            Thread.sleep(1000);
            litab_DSNguoidung.click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean changeShopcode(C2UserAdminDTO dto, User user) {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            return chonDSNguoiDung() ? change(dto) : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean change(C2UserAdminDTO dto) {
        try {
            System.out.println("XXX:" + dto.toString());
            WebElement txtSearch = driver.findElement(By.xpath("//input[@placeholder='Nhập tìm kiếm theo tên đăng nhập, tên người dùng, tài khoản , mô tả']"));
            Thread.sleep(1000);
            txtSearch.sendKeys(dto.getUsername());
            Thread.sleep(3000);
            txtSearch.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            WebElement txtResult = driver.findElement(By.xpath("//td[normalize-space()='" + dto.getUsername() + "']"));
            Thread.sleep(1000);
            txtResult.click();
            Thread.sleep(1000);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,250)");
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[contains(text(),'Sửa')]")).click();
            Thread.sleep(1000);
            WebElement btnShop_code = driver.findElement(By.xpath("(//*[@data-icon='down'])[8]"));
            btnShop_code.click();
            WebElement txtShop_code = driver.findElement(By.xpath("//div[@class='col-md-6']//div[1]//div[1]//div[1]//div[1]//div[2]//div[1]//input[1]"));
            txtShop_code.sendKeys(dto.getShopCode());
            Thread.sleep(1000);
            txtShop_code.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(1000);
            txtShop_code.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            WebElement btnToThu = driver.findElement(By.xpath("//body/div/div/div[@role='dialog']/div[@role='document']/div[@class='modal-content']/div[@id='dialog']/div[@class='modal-body']/div[@class='modal-body']/form[@id='mainForm']/div[@class='row']/div[1]/div[4]/div[2]/div[1]/div[1]/span[1]"));
            btnToThu.click();
            WebElement txtTothu = driver.findElement(By.xpath("(//input[@class='ant-select-search__field'])[1]"));
            txtTothu.sendKeys(dto.getShopCode());
            Thread.sleep(1000);
            txtTothu.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(1000);
            txtTothu.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            jse.executeScript("window.scrollBy(0,250)");
            Thread.sleep(1000);
            if (dto.getGranted_ip() != null) {
                if (!dto.getGranted_ip().trim().equals("")) {
                    WebElement grantedIp = driver.findElement(txtGrantedIp);
                    grantedIp.clear();
                    Thread.sleep(1000);
                    grantedIp.sendKeys(dto.getGranted_ip());
                    Thread.sleep(1000);
                }
            }
            if (driver.findElement(txtPhone).getAttribute("value").trim().equals("")) {
                driver.findElement(txtPhone).sendKeys("1");
            }   // CLICK
            WebElement btnLuuThongTin = driver.findElement(By.xpath("//button[contains(text(),'Lưu thông tin')]"));
            btnLuuThongTin.click();
            Thread.sleep(1000);
            driver.quit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inactivityUser(String username, User user) throws InterruptedException {
        setUp();
        signInPage = new SignInPage(driver);
        signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
        return chonDSNguoiDung() ? nghiviecNV(username) : false;
    }

    private boolean nghiviecNV(String username) throws InterruptedException {
        WebElement txtSearch = driver.findElement(By.xpath("//input[@placeholder='Nhập tìm kiếm theo tên đăng nhập, tên người dùng, tài khoản , mô tả']"));
        Thread.sleep(1000);
        txtSearch.sendKeys(username);
        Thread.sleep(1000);
        txtSearch.sendKeys(Keys.ENTER);
        try {
            if (driver.findElements(By.xpath("//div[contains(text(),'Không có bản ghi nào thỏa mãn điều kiện')]")).size() != 0) {
                System.out.println("Khong co user");
                return true;
            } else {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("window.scrollBy(0,250)");
                Thread.sleep(2000);
                driver.findElement(By.xpath("//button[contains(text(),'Sửa')]")).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath("//div[@id='calendarAccess']//div[@role='combobox']")).click(); // lich truy nhap
                Thread.sleep(1000); //c8_nghiviec
                driver.findElement(By.xpath("//li[normalize-space()='" + Constants.RETIRE_SCHEDULER + "']")).click();
                WebElement grantedIp = driver.findElement(txtGrantedIp);
                grantedIp.clear();
                Thread.sleep(1000);
                grantedIp.sendKeys(Constants.IP_DEFAULT);
                jse.executeScript("window.scrollBy(0,250)");
                Thread.sleep(1000);
                WebElement selectAll_Permission = driver.findElement(By.xpath("//div[@class='ant-table-selection']//input[@type='checkbox']"));
                if (selectAll_Permission.isEnabled()) {
                    selectAll_Permission.click();
                    WebElement btnDeletedPermission = driver.findElement(By.xpath("//section[@class='code-box expand']//button[@type='button'][normalize-space()='Xóa']"));
                    btnDeletedPermission.click();
                }  // CLICK
                driver.findElement(By.xpath("//button[contains(text(),'Lưu thông tin')]")).click();
                Thread.sleep(5000);
                driver.quit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
            return false;
        }
    }
}
