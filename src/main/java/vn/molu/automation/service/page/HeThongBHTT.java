package vn.molu.automation.service.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import vn.molu.automation.service.basic.BasicSetup;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.domain.admin.GroupUserPermission;
import vn.molu.domain.admin.User;

import java.util.List;

@Service
public class HeThongBHTT extends BasicSetup {
    private WebDriver driver;
    private String chromeLocationUrl;
    private String seleniumServer;
    public SignInPage signInPage;
    private static final String appUrl = "http://10.3.7.198/admin/";
    private By txtUsername_Login = By.xpath("//input[@id='username']");
    private By txtPassword_Login = By.xpath("//input[@id='password']");
    private By btnLogin = By.xpath("//button[contains(text(),'Đăng nhập')]");
    private By btnShow_Menu = By.xpath("//button[@class='p-link layout-menu-button']");
    private By btnQuanTriHeThong = By.xpath("//span[@title='Quản trị hệ thống']");
    private By btnDanhSachNSD_NhomNSD = By.xpath("//span[@title='3. Danh sách NSD và Nhóm NSD']");
    private By btnAccessSchedule = By.xpath("//span[@title='4. Danh sách lịch truy nhập']");
    private By btnIP_Granted = By.xpath("//span[@title='5. Danh sách IP']");
    private By input_tenLogin = By.xpath("//th[3]//input[1]");
    private By btnUpdate_user = By.xpath("//span[contains(@class,'pi pi-pencil p-c p-button-icon-left')]");
    private By searchRs = By.xpath("//label[contains(text(),'bản ghi:')]");
    private By txt_shopCode = By.xpath("//input[@id='shopCode']");
    private By btnLuuDuLieu_Update = By.xpath("//span[contains(text(),'LƯU DỮ LIỆU')]");
    private By cbbStatusInput = By.xpath("//div[@id='status']//div[@role='combobox']");
    private By txt_UserInput = By.xpath("//input[@id='userName']");
    private By txt_FullNameInput = By.xpath("//input[@id='fullName']");
    private By txt_PasswordInput = By.xpath("//input[@id='password']");
    private By txt_ConfirmPasswordInput = By.xpath("//input[@id='conFirm']");
    private By cbb_LDAPInput = By.xpath("//div[@id='checkLDAP']//div[@class='ant-select-selection__rendered']");
    private By cbb_PasswordExpiredStatusInput = By.xpath("//div[@id='passwordExpireStatus']//div[@class='ant-select-selection__rendered']");
    private By modal_Rs = By.xpath("//div[contains(text(),'Thực hiện thành công!')]");
    private By btnClose_message = By.xpath("//button[@id='btnCloseMessage']");
    private By btnModal_Rs_Thoat = By.xpath("//span[normalize-space()='THOÁT']");

    public void setUp() {
        driver = getDriver(appUrl, chromeLocationUrl, seleniumServer);
    }

    public HeThongBHTT() {
    }

    public HeThongBHTT(String chromeLocationUrl, String seleniumServer) {
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

    public boolean changeShopcode(String user_change, String shopcode_new, User user) {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            Thread.sleep(1000);
            return clickQuanLyHeThong(btnDanhSachNSD_NhomNSD) ? change(user_change, shopcode_new) : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean inactiveUser(String userName, User user) {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, user);
            Thread.sleep(1000);
            return clickQuanLyHeThong(btnDanhSachNSD_NhomNSD) ? inactiveUserDetail(userName) : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean inactiveUserDetail(String userName) throws InterruptedException {
        WebElement input_user = driver.findElement(input_tenLogin);
        Thread.sleep(1000);
        input_user.sendKeys(userName);
        Thread.sleep(1000);
        String searchUserRs = driver.findElement(searchRs).getText().split(":")[1];
        Thread.sleep(1000);
        if (Integer.parseInt(searchUserRs.trim()) > 0) { // co user do
            Thread.sleep(1000);
            driver.findElement(btnUpdate_user).click();
            Thread.sleep(1000);
            driver.findElement(cbbStatusInput).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//li[@label='Không hoạt động']")).click();
            Thread.sleep(1000);
            driver.findElement(btnLuuDuLieu_Update).click();
            Thread.sleep(2000);
            return getResultProceess();
        } else {// sai USER - ko co
            driver.quit();
            return false;
        }
    }

    private boolean change(String user_change, String shopcode_new) throws InterruptedException {
        WebElement input_user = driver.findElement(input_tenLogin);
        Thread.sleep(1000);
        input_user.sendKeys(user_change);
        Thread.sleep(1000);
        String searchUserRs = driver.findElement(searchRs).getText().split(":")[1];
        Thread.sleep(1000);
        if (Integer.parseInt(searchUserRs.trim()) > 0) { // co user do
            Thread.sleep(1000);
            driver.findElement(btnUpdate_user).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='shopCode']")).click();
            Thread.sleep(1000);
            driver.findElement(txt_shopCode).sendKeys(shopcode_new);
            Thread.sleep(1000);
            List<WebElement> searchShopRS = driver.findElements(By.xpath("//div[@title='" + shopcode_new + "']"));
            Thread.sleep(4000);
            if (searchShopRS.size() > 0) {
                Thread.sleep(1000);
                driver.findElement(By.xpath("//div[contains(text(), '" + shopcode_new + "')]")).click();
                Thread.sleep(1000);
                //c1
//                WebElement btnLuu = driver.findElement((By.xpath("//body/div[6]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/button[1]")));
//                Thread.sleep(1000);
//                btnLuu.click();
                //c2
//                waitEmlement(driver, 3000, "//span[contains(text(),'LƯU DỮ LIỆU')]");
                //c3
                driver.findElement(btnLuuDuLieu_Update).click();
//                System.out.println("c4");
//                driver.findElement(By.xpath("//button[contains(@color,'primary')]")).click();
                Thread.sleep(1000);
                return getResultProceess();
            } else {// sai shop-code
                driver.quit();
                return false;
            }
        } else {// ko co user do
            driver.quit();
            return false;
        }
    }

    public Boolean createUser(C2AdminUserAuto user, User userLogin, List<GroupUserPermission> groupUsers) {
        try {
            setUp();
            signInPage = new SignInPage(driver);
            signInPage.signin(driver, appUrl, txtUsername_Login, txtPassword_Login, btnLogin, userLogin);
            Thread.sleep(1000);
            return clickQuanLyHeThong(btnDanhSachNSD_NhomNSD) ? themUser(user, groupUsers) : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean themUser(C2AdminUserAuto user, List<GroupUserPermission> groupUsers) {
        try { // phan1: Thong tin truy cap
            driver.findElement(By.xpath("//span[@class='p-button-text p-c'][contains(text(),'Thêm mới')]")).click();
            Thread.sleep(1000);
            driver.findElement(txt_UserInput).sendKeys(user.getUser_name());
            Thread.sleep(1000);
            driver.findElement(txt_FullNameInput).sendKeys(user.getFull_name());
            Thread.sleep(1000);
            driver.findElement(txt_PasswordInput).sendKeys(user.getPassword());
            Thread.sleep(1000);
            driver.findElement(txt_ConfirmPasswordInput).sendKeys(user.getPassword());
            Thread.sleep(1000);
            driver.findElement(cbb_LDAPInput).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//li[@label='Có kiểm tra']")).click();
            Thread.sleep(1000);
            driver.findElement(cbbStatusInput).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//li[@label='Đang hoạt động']")).click();
            Thread.sleep(1000);
            driver.findElement(cbb_PasswordExpiredStatusInput).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//li[@label='Đang sử dụng']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='shopCode']")).click();
            Thread.sleep(1000);
            driver.findElement(txt_shopCode).sendKeys(user.getShop_code());
            Thread.sleep(1000);
            List<WebElement> searchShopRS = driver.findElements(By.xpath("//div[@title='" + user.getShop_code() + "']"));
            Thread.sleep(4000);
            if (searchShopRS.size() > 0) {
                Thread.sleep(1000);
                driver.findElement(By.xpath("//div[contains(text(), '" + user.getShop_code() + "')]")).click();
                Thread.sleep(1000);
            }   // phan2: quyen truy cap
            return authororityUser(user.getUser_name(), groupUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean authororityUser(String user_name, List<GroupUserPermission> groupUsers) {
        try {
            if (!groupUsers.isEmpty()) {
                driver.findElement(By.xpath("//span[contains(text(),'Thuộc nhóm')]")).click();
                Thread.sleep(1000);
                groupUsers.forEach(group -> {
                    WebElement txtInput_Search = driver.findElement(By.xpath("//div[@class='p-scrollpanel-content']//input[@type='text']"));
                    txtInput_Search.clear();
                    try {
                        Thread.sleep(1000);
                        txtInput_Search.sendKeys(group.getPermission());
                        Thread.sleep(1000);
                        driver.findElement(By.xpath("//div[@class='p-checkbox-box']")).click();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                driver.findElement(btnLuuDuLieu_Update).click();
                Thread.sleep(1000);
                List<WebElement> resultProcess = driver.findElements(modal_Rs);
                Thread.sleep(2000);
                if (!resultProcess.isEmpty()) { // them OK
                    driver.findElement(btnClose_message).click();
                    Thread.sleep(1000); // THEM USER - b3:
                    grantIpAndAccessTime(user_name);
                    return true;
                } else { // them moi User ko thanh cong
                    driver.quit();
                    return false;
                }
            } else { // ko co nhom quyen nao` cua GDV
                driver.quit();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
            return false;
        }
    }

    private boolean getResultProceess() {
        try {
            List<WebElement> rs = driver.findElements(modal_Rs);
            Thread.sleep(5000);
            if (!rs.isEmpty()) {
                driver.findElement(btnClose_message).click();
                Thread.sleep(1000);
//                driver.quit();
                return true;
            } else {
                driver.quit();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean grantIpAndAccessTime(String username) { // chọn tab - danh sách lịch truy nhập
        try {
            driver.findElement(btnShow_Menu).click();
            Thread.sleep(1000);
            driver.findElement(btnAccessSchedule).click();
            Thread.sleep(1000);
            return grantAccessTime(username) ? granIP(username) : false;
        } catch (Exception e) {
            e.printStackTrace();
//            driver.quit();
            return false;
        }
    }

    private boolean grantAccessTime(String username) {
        try {
            WebElement input_Name = driver.findElement(By.xpath("//input[contains(@class,'p-inputtext p-component p-column-filter')]"));
            input_Name.clear();
            Thread.sleep(1000);
            input_Name.sendKeys("access everyday and everytime");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[@class='pi pi-pencil p-c p-button-icon-left']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[contains(text(),'Người sử dụng')]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[contains(text(),'Tất cả nhóm quyền')]")).click();
            Thread.sleep(2000);
            WebElement input_tenLogin = driver.findElement(By.xpath("//div[contains(@role,'dialog')]//th[3]//input[1]"));
            Thread.sleep(1000);
            input_tenLogin.sendKeys(username);
            Thread.sleep(1000);
            WebElement searchResult = driver.findElement(By.xpath("//div[contains(@class,'p-fieldset-content')]//div[contains(@class,'col-md-12')]//div[contains(@class,'p-datatable p-component p-datatable-resizable p-datatable-resizable-fit p-datatable-scrollable p-datatable-hoverable-rows')]//label[contains(text(),'n ghi:')]"));
            Thread.sleep(1000);
            String searchRowNum = searchResult.getText().split(":")[1];
            Thread.sleep(1000);
            if (Integer.parseInt(searchRowNum.trim()) != 0) { // có user
                WebElement cbbSelected = driver.findElement(By.xpath("//tr[@class='p-datatable-row']//div[@role='checkbox']"));
                if (cbbSelected.getAttribute("aria-checked").equals("false")) {
                    System.out.println("CHUA CHON SCHEDULER");
                    Thread.sleep(1000);
                    cbbSelected.click();
                } else { // user đã đc cấp rồi - bỏ qua buớc này
                    System.out.println("DA CHON SCHEDULER");
                    return true;
                }
                Thread.sleep(1000);
                driver.findElement(btnLuuDuLieu_Update).click();
                return getResultProceess();
            } else { // ko co user
                driver.findElement(btnModal_Rs_Thoat).click();
                Thread.sleep(1000);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean granIP(String username) {
        try {
            driver.findElement(btnShow_Menu).click();
            Thread.sleep(1000);
//            driver.findElement(btnQuanTriHeThong).click();
//            Thread.sleep(1000);
            driver.findElement(btnIP_Granted).click();
            Thread.sleep(1000);
            return granIP_Detail(username);
        } catch (Exception e){
            e.printStackTrace();
            driver.quit();
            return false;
        }
    }

    private boolean granIP_Detail(String username) {
        try {
            WebElement input_Name = driver.findElement(By.xpath("(//input[@type='text'])[1]"));
            input_Name.clear();
            Thread.sleep(1000);
            input_Name.sendKeys("Public IP");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[@class='pi pi-pencil p-c p-button-icon-left']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[contains(text(),'Cấp quyền sử dụng')]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[contains(text(),'Tất cả nhóm quyền')]")).click();
            Thread.sleep(2000);
            WebElement input_username = driver.findElement(By.xpath("//div[contains(@role,'dialog')]//th[3]//input[1]"));
            input_username.sendKeys(username);
            Thread.sleep(1000);
            WebElement searchResult = driver.findElement(By.xpath("//div[contains(@class,'p-fieldset-content')]//div[contains(@class,'col-md-12')]//div[contains(@class,'p-datatable p-component p-datatable-resizable p-datatable-resizable-fit p-datatable-scrollable p-datatable-hoverable-rows')]//label[contains(text(),'n ghi:')]"));
            Thread.sleep(1000);
            String searchRowNum = searchResult.getText().split(":")[1];
            Thread.sleep(1000);
            if (Integer.parseInt(searchRowNum.trim()) != 0) { // tim ra user
                WebElement cbbSelected = driver.findElement(By.xpath("//tr[@class='p-datatable-row']//div[@role='checkbox']"));
                if (cbbSelected.getAttribute("aria-checked").equals("false")) {
                    System.out.println("CHUA CHON IP");
                    cbbSelected.click();
                } else {
                    System.out.println("DA CHON IP -> KHOI CHON");
                    return true;
                }
                Thread.sleep(1000);
                driver.findElement(btnLuuDuLieu_Update).click();
                Thread.sleep(1000);
                driver.findElement(btnClose_message).click(); // close modal BHTT
                driver.quit();
                return true;
            } else { // ko co user
                driver.findElement(btnModal_Rs_Thoat).click();
                Thread.sleep(1000);
                driver.quit();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
            return false;
        }
    }

    private boolean clickQuanLyHeThong(By danhmucCanChon) {
        try {
            driver.findElement(btnShow_Menu).click();
            Thread.sleep(1000);
            driver.findElement(btnQuanTriHeThong).click();
            Thread.sleep(1000);
            driver.findElement(danhmucCanChon).click();
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean waitEmlement(WebDriver driver, int timeout, String path) {
        boolean flag = false;
        int counter = 0;
        while (!flag) {
            try {
                if (driver.findElement(By.xpath(path)).isDisplayed()) {
                    flag = true;
                    System.out.println("CO MAT");
                    driver.findElement(By.xpath(path)).click();
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
}
