package vn.molu.webapp.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.GenericJDBCException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.automation.service.page.HeThongBHTT;
import vn.molu.automation.service.page.HeThongCSKH;
import vn.molu.automation.service.page.HeThongTTCP;
import vn.molu.automation.service.page.QuanTriPhanQuyenUser;
import vn.molu.common.Constants;
import vn.molu.common.utils.RequestUtil;
import vn.molu.dao.temp.C2UserAdminDAO;
import vn.molu.dao.temp.ShopDAO;
import vn.molu.domain.admin.Program;
import vn.molu.domain.admin.User;
import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.dto.admin.admin.EmployeeDTO;
import vn.molu.dto.admin.admin.ShopDTO;
import vn.molu.service.ResponseContainer;
import vn.molu.service.admin.*;
import vn.molu.service.exception.HandleExceptionService;
import vn.molu.service.request.ServerSideRequest;
import vn.molu.webapp.command.admin.C2AdminUserCommand;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class C2UserController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    //XXXXXX Chưa validate XXXXXXXXXXXXXXXXXXXXX

    @Autowired
    private C2UserAdminDAO c2UserAdminDAO;
    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private C2UserAdminService c2UserAdminService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private ExplanationFormService explanationFormService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CSKK_UserService cskkUserService;
    @Autowired
    private Environment environment;
    @Autowired
    private HandleExceptionService handleExceptionService;
    @Value("${system.chrome.path}")
    private String chromeLocationUrl;

    @RequestMapping(value = "/ajax/getC2UserByUsername.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    C2UserAdminDTO test(@RequestParam("userId") String user_id) {
        try {
            C2UserAdminDTO user = c2UserAdminDAO.findByUserId(Long.parseLong(user_id));
            return user;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new C2UserAdminDTO();
        }
    }

    @RequestMapping("/list/user_c2_ss.html")
    public ModelAndView list2(@ModelAttribute(value = Constants.FORM_MODEL_KEY) C2AdminUserCommand command,
                              HttpServletRequest request, BindingResult bindingResult) throws InterruptedException, ParseException, MalformedURLException {
        RequestUtil.initSearchBean(request, command); // chua hiu lam`z`???
        ModelAndView mav = new ModelAndView("admin/c2user/list-server-side");
        String crudaction = command.getCrudaction();
        C2UserAdminDTO pojo = command.getPojo();
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_UNLOCK)) {
            Long userId = command.getPojo().getUserId();
            if (userId != null) {
                try {
                    C2UserAdminDTO dto = c2UserAdminDAO.findByUserId(userId);
                    System.out.println("DTO: " + dto);
                    if (dto.getStatus().equals("4") || dto.getStatus().equals("2")) { // using
                        c2UserAdminDAO.updateStatusC2UserAdmin(userId, "1");
                    } else { // unlock
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = formatter.parse(command.getUploadDate());
                        String username = command.getPojo().getUsername().toUpperCase();
                        explanationFormService.uploadFile(command.getFile(), date, username, "2");
                        c2UserAdminDAO.updateStatusC2UserAdmin(userId, "4");
                    }
                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                            this.getMessageSourceAccessor().getMessage("msg.database.unlock.successful"));
                } catch (Exception e) {
                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                            this.getMessageSourceAccessor().getMessage("msg.database.unlock.unsuccessful"));

                    log.error(e.getMessage(), e);
                }
            }
            command.setPojo(null);
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_CHANGE_SHOP)) { // đôi shop_code cho user
            if (command.getProgram() != null) {
                changeShopcode(mav, command);
            }
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)) {
            if (!bindingResult.hasErrors()) { // update user: status, ip cho user, quyền cho CHT
                if (pojo.getUserId() != null) {
                    // chi dc update 1 trong 2 loại (vì đây là 2 chương trình khác nhau - o liên quan)
                    if (command.getEmail().trim() != null) { // quyền duyệt số của CHT
                        try {
                            boolean validateMail = cskkUserService.findMailInHr_Nhanvien_Alldata(command.getEmail());
                            if (validateMail) {
                                Integer rsCSKK = cskkUserService.grand_DuyetSoPermission_ForCHT(command.getEmail());
                                if (rsCSKK > 0) {
                                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                            this.getMessageSourceAccessor().getMessage("msg.CSKK.CHT_duyetso.successful"));
                                } else {
                                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                            this.getMessageSourceAccessor().getMessage("msg.CSKK.CHT_duyetso.unsuccessful"));
                                }
                            } else {
                                mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                                mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                        this.getMessageSourceAccessor().getMessage("msg.CSKK.CHT_duyetso.unsuccessful"));
                            }
                        } catch (Exception e) {
                            mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                    this.getMessageSourceAccessor().getMessage("msg.CSKK.CHT_duyetso.unsuccessful"));

                            log.error(e.getMessage(), e);
                        }
                    } else {
                        try {
                            Long id = command.getPojo().getUserId();
                            String newStatus = command.getPojo().getStatus();
                            String newIP = command.getPojo().getGranted_ip();
                            c2UserAdminDAO.update(id, newStatus, newIP);

                            mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                    this.getMessageSourceAccessor().getMessage("msg.database.update.successful"));
                        } catch (Exception e) {
                            mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                    this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful"));

                            log.error(e.getMessage(), e);
                        }
                    }
                } else {
                    // ADD
                }
                // reset modal form
                command.setPojo(null);
            } else {
                mav.addObject(Constants.IS_MODAL_SHOW, true);
            }
        }
        List<ShopDTO> listShop = shopDAO.findActiveShopCenCode2("2", "1");
        mav.addObject("listShop", listShop);
        mav.addObject("listShop2", listShop);
        List<Program> programList = programService.findAll();
        mav.addObject("listProgram", programList);
        return mav;
    }

    private void changeShopcode(ModelAndView mav, C2AdminUserCommand command) throws InterruptedException, MalformedURLException {
        command.getPojo().setStatus("1"); // ?????
        Map<String, Integer> result_OneUserForAllProgram = new HashMap<>(); // lay kq User
        List<EmployeeDTO> employListHistory = employeeService.findActiveUserByUsername(command.getPojo().getUsername());
        List<String> listIncorrectDataUser = new ArrayList<>(); // Lỗi - dữ liệu đầu vao ko hợp lệ
        User loginUser = userService.findByUsername(); // USER dang login
        if (employeeService.checkUserIsGDV(command.getPojo().getUsername()).equals("YES")) { //CHECK GDV
            if (command.getProgram().contains(Constants.PROGRAM_ID_TTCP)) {
                HeThongTTCP ttcp = new HeThongTTCP(chromeLocationUrl);
                boolean rsTTCP = ttcp.changeShopcode(command.getPojo(), command.getShopcode_old(), loginUser);
                result_OneUserForAllProgram.put(Constants.PROGRAM_TTCP, rsTTCP == true ? 1 : 0);
            }
            // tim ds Shop code cua nguoi do:
            if (command.getProgram().contains(Constants.PROGRAM_ID_CSKH)) {
                HeThongCSKH cskh = new HeThongCSKH(chromeLocationUrl);
                // tìm lại thông tin của User đó để tìm loại NV -> chưa xong, làm lại
                EmployeeDTO employeeDTO = employeeService.findActiveEmp_codeByUsername(command.getPojo().getUsername());
                if (employeeDTO != null && employeeDTO.getEmp_type() != null) {
                    command.getPojo().setAccount(String.valueOf(employeeDTO.getEmp_type()));
                } else {
                    command.getPojo().setAccount("1");
                } // chi lam` GDV -> TRONG TABLE NAY` TOANF USER -> GDV 100%:
                boolean rsCSKH = cskh.changeShopcode(command.getPojo(), command.getShopcode_old(), loginUser); // c2adminuser.account -> loai user
                result_OneUserForAllProgram.put(Constants.PROGRAM_CSKH, rsCSKH == true ? 1 : 0);
            }
            if (command.getProgram().contains(Constants.PROGRAM_ID_BHTT)) {
                HeThongBHTT heThongBHTT = new HeThongBHTT(chromeLocationUrl);
                boolean rsBHTT = heThongBHTT.changeShopcode(command.getPojo().getUsername(), command.getPojo().getShopCode(), userService.getUserLogin_BHTT_System());
                result_OneUserForAllProgram.put(Constants.PROGRAM_BHTT, rsBHTT == true ? 1 : 0);
            }
        } else { // loai User AM
            listIncorrectDataUser.add(environment.getProperty("msg.incorrect_mail_message.wrong_user_AM")
                    .replace("USER", command.getPojo().getUsername()));
        }
        if (command.getProgram().contains(Constants.PROGRAM_ID_DTHGD)) {
            QuanTriPhanQuyenUser qtpq = new QuanTriPhanQuyenUser(chromeLocationUrl);
            if (command.getPojo().getGranted_ip() == null || command.getPojo().getGranted_ip().equals("")) {
                listIncorrectDataUser.add(environment.getProperty("msg.incorrect_mail_message.wrong_ip")
                        .replace("SHOPCODE", command.getPojo().getShopCode()));
            }
            boolean rsDTHGD = qtpq.changeShopcode(command.getPojo(), loginUser);
            result_OneUserForAllProgram.put(Constants.PROGRAM_DTHGD, rsDTHGD == true ? 1 : 0);
        }
        if (command.getProgram().contains(Constants.PROGRAM_ID_CSKK)) {
            if (employListHistory.size() > 0) {
                try {
                    Integer rsCSKK = employeeService.changeShopcodeInCSKK(command.getEmail(), command.getPojo().getShopCode());
                    System.out.println("RSCSKK:" + rsCSKK);
                    result_OneUserForAllProgram.put(Constants.PROGRAM_CSKK, rsCSKK > 0 ? 1 : 0);
                } catch (Exception e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof GenericJDBCException) { // mail ko tồn tại trên hệ thống
                        // loi
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_INCORRECT_MAIL, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY_INCORRECT,
                                environment.getProperty("msg.incorrect_mail_message").replace("INCORRECT_MAIL", command.getEmail()));
                        result_OneUserForAllProgram.put(Constants.PROGRAM_CSKK, 0);
                    }
                }
            }
        }
        if (command.getProgram().contains(Constants.PROGRAM_ID_RESNUM)) {
            Integer rsResum = employeeService.changeShopCodeInResnum(command.getPojo().getUsername(), command.getPojo().getShopCode());
            result_OneUserForAllProgram.put(Constants.PROGRAM_RESNUM, rsResum > 0 ? 1 : 0);
        }
        if (listIncorrectDataUser.size() > 0) { // Thong bao dữ liêu đầu vào ko đúng:
            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_INCORRECT_MAIL, true);
            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY_INCORRECT,
                    environment.getProperty("msg.incorrect_mail_message").replace("INCORRECT_MAIL", handleExceptionService.convertIncorrectDataInput(listIncorrectDataUser)));
        }

        // BANG KQ TAT CA TIEN TRINH:
        Map<String, String> result_AllUserAllProgram_ShowMessage = new HashMap<>();
        result_AllUserAllProgram_ShowMessage.put(command.getPojo().getUsername(), handleExceptionService.convertResultOneUserForAllProgram(result_OneUserForAllProgram));
        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_DIFFICULT, true);
        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, result_AllUserAllProgram_ShowMessage);
    }

    @RequestMapping(value = "/ajax/user_c2_ss.html", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    ResponseContainer testSS(@RequestBody ServerSideRequest ssr) {
        return c2UserAdminService.getListing(ssr);
    }
}
