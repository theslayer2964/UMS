package vn.molu.webapp.controller.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.automation.service.page.HeThongBHTT;
import vn.molu.automation.service.page.HeThongCSKH;
import vn.molu.automation.service.page.HeThongTTCP;
import vn.molu.automation.service.page.QuanTriPhanQuyenUser;
import vn.molu.common.Constants;
import vn.molu.common.utils.RequestUtil;
import vn.molu.dao.temp.ShopDAO;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.domain.admin.GroupUser;
import vn.molu.domain.admin.User;
import vn.molu.dto.admin.admin.*;
import vn.molu.service.admin.*;
import vn.molu.service.excel.ExcelReader;
import vn.molu.service.exception.HandleExceptionService;
import vn.molu.webapp.command.admin.AddUserFileCommand;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


@Controller
public class AddUserByFileController extends ApplicationObjectSupport {

    @Autowired
    private AutoC2UserService autoC2UserService;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private C2UserAdminService c2UserAdminService;
    @Autowired
    private Resnum_UserService resnumUserService;
    @Autowired
    private CSKK_UserService cskkUserService;
    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private ShopCodeIpGrantedService shopCodeIpGrantedService;
    @Autowired
    private Environment environment;
    @Autowired
    private HandleExceptionService handleExceptionService;
    @Value("${system.selenium-server.path}")
    private String seleniumServer;
    @Value("${system.firefox.driver}")
    private String firefoxDriver;

    @RequestMapping("/list/add_user_c2_file.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) AddUserFileCommand command,
                             HttpServletRequest request, BindingResult bindingResult) throws Exception {
        RequestUtil.initSearchBean(request, command);
        ModelAndView mav = new ModelAndView("admin/addC2user/insertFile");
        String crudaction = command.getCrudaction();
        List<C2AdminUserAuto> addUserList = new ArrayList<>();
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_ADD)) { // add user // cach cu // ko xai` nua
            if (!bindingResult.hasErrors()) {
                if (command.getFileType().equals("add")) {
                    if (!command.getFile().isEmpty()) {
                        ExcelReader excelReader = new ExcelReader(command.getFile());
                        addUserList = excelReader.readAddUserExcelFile();
                        command.setFileType("add");
                    }
                } else if (command.getFileType().equals("update")) {
                    if (!command.getFile().isEmpty()) {
                        ExcelReader excelReader = new ExcelReader(command.getFile());
                        addUserList = excelReader.readAddUserExcelFile();
                        command.setFileType("update");
                    }
                }
            }
        } else if ((StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.SAVE_MANY_USER_BY_FILE))) {
            if (!bindingResult.hasErrors()) {
                if (command.getFileType().equals("add")) { // them user
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<C2AdminUserAuto> list = objectMapper.readValue(command.getListUserAuto(), new TypeReference<List<C2AdminUserAuto>>() {
                    });
                    Map<String, String> result_AllUserAllProgram_ShowMessage = new HashMap<>(); // xuat KQ
                    List<String> listIncorrectDataUser = new ArrayList<>(); // Lỗi - dữ liệu đầu vao ko hợp lệ
                    User loginUser = userService.findByUsername();
                    list.forEach(s -> {
                        s.setPassword(Constants.PASSWORD_DEFAULT); // DATA NAy` CHUA CO -> SET PASSWORD VAO
                        System.out.println("SSS:" + s);
                        Map<String, Integer> result_OneUserForAllProgram = new HashMap<>();
                        try {
                            if (s.getProgram().contains(Constants.PROGRAM_ID_DTHGD)) {
                                if (s.getGranted_ip() == null || s.getGranted_ip().trim().equals("")) {//Chua co -> set lai granted_ip
                                    if (s.getShop_code() != null) {
                                        String newGrantedIP = shopCodeIpGrantedService.getIP_Granted_ByShopCode(s.getShop_code());
                                        s.setGranted_ip(newGrantedIP != null ? newGrantedIP : Constants.IP_DEFAULT);
                                        if (newGrantedIP == null) {
                                            listIncorrectDataUser.add(environment.getProperty("msg.incorrect_mail_message.wrong_ip")
                                                    .replace("SHOPCODE", s.getShop_code()));
                                        }
                                    }
                                }
                                List<GroupUser> listGroupUserPermission = groupUserService.findGroupUserByRoleNProgram(s.getGroupUser(), Constants.PROGRAM_ID_DTHGD);
                                QuanTriPhanQuyenUser quanTriPhanQuyenUser = new QuanTriPhanQuyenUser(firefoxDriver, seleniumServer);
                                Boolean rsDTHGD = quanTriPhanQuyenUser.createUser(s, listGroupUserPermission, loginUser);
                                result_OneUserForAllProgram.put(Constants.PROGRAM_DTHGD, rsDTHGD == true ? 1 : 0);
                            }
                            if (s.getProgram().contains(Constants.PROGRAM_ID_TTCP)) {
                                HeThongTTCP heThongTTCP = new HeThongTTCP(firefoxDriver,seleniumServer);
                                Boolean rsTTCP = heThongTTCP.createUser(s, loginUser);
                                result_OneUserForAllProgram.put(Constants.PROGRAM_TTCP, rsTTCP == true ? 1 : 0);
                            }
                            if (s.getProgram().contains(Constants.PROGRAM_ID_BHTT)) {
                                HeThongBHTT heThongBHTT = new HeThongBHTT(firefoxDriver, seleniumServer);
                                List<GroupUser> listGroupUserPermission = groupUserService.findGroupUserByRoleNProgram(s.getGroupUser(), Constants.PROGRAM_ID_BHTT);
                                Boolean rsBHTT = heThongBHTT.createUser(s, userService.getUserLogin_BHTT_System(), listGroupUserPermission);
                                result_OneUserForAllProgram.put(Constants.PROGRAM_BHTT, rsBHTT == true ? 1 : 0);
                            }
                            if (s.getProgram().contains(Constants.PROGRAM_ID_CSKH)) {
                                HeThongCSKH heThongCSKH = new HeThongCSKH(firefoxDriver, seleniumServer);
                                s.setDistrict(Constants.DISTRICT_DEFAULT);
                                Boolean rsCSKH = heThongCSKH.createUser(s, Constants.CITY_DEFAULT, loginUser);
                                result_OneUserForAllProgram.put(Constants.PROGRAM_CSKH, rsCSKH == true ? 1 : 0);
                            }
                            if (s.getProgram().contains(Constants.PROGRAM_ID_CSKK)) { // add user CSKK
                                CSKK_UserDTO cskkUserDTO = new CSKK_UserDTO();
                                cskkUserDTO.setUsername(s.getUser_name());
                                cskkUserDTO.setEmail(s.getEmail());
                                cskkUserDTO.setPosition(s.getPosition());
                                cskkUserDTO.setShopCode(s.getShop_code());
                                if (s.getPosition().equals("AM")) // am
                                    cskkUserDTO.setBranch(s.getToThu());
                                else if (s.getPosition().equals("GDV") || s.getPosition().equals("CHT"))  // gdv
                                    cskkUserDTO.setBranch(s.getShop_code());
                                else { // dlc
                                    cskkUserDTO.setBranch(s.getToThu());
                                    cskkUserDTO.setShopCode(s.getToThu());
                                }
                                cskkUserDTO.setPhone(s.getPhone());
                                cskkUserDTO.setFullName(s.getFull_name());
                                cskkUserDTO.setStatus(Constants.CSKK_Status_0);
                                ShopDTO shopDTO = shopDAO.findById(s.getShop_code());
                                cskkUserDTO.setShopName(shopDTO.getName());
                                // b1: delete temp
                                List<CSKK_UserDTO> tempSize = cskkUserService.getListCSKK_DTO();
                                if (tempSize.size() > 0) {
                                    cskkUserService.deleteTemp();
                                }
                                // b2: add new user + execute procedure
                                cskkUserService.insert(cskkUserDTO);
                                CSKK_UserDTO rsCSKK = cskkUserService.findCSKK_UserByUsername(s.getUser_name());
                                result_OneUserForAllProgram.put(Constants.PROGRAM_CSKK, rsCSKK.getStatus() == 1 ? 1 : 0);
                            }
                            if (s.getProgram().contains(Constants.PROGRAM_ID_RESNUM)) {// RESNUM:
                                Resum_UserDTO resnumUser = new Resum_UserDTO();
                                resnumUser.setUser_tracuu(s.getUser_name());
                                Integer rsResum = resnumUserService.insert(resnumUser);
                                result_OneUserForAllProgram.put(Constants.PROGRAM_RESNUM, rsResum > 0 ? 1 : 0);
                            }
                            result_AllUserAllProgram_ShowMessage.put(s.getUser_name(), handleExceptionService.convertResultOneUserForAllProgram(result_OneUserForAllProgram));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        autoC2UserService.save(s);
                    });

                    if (listIncorrectDataUser.size() > 0) { // Thong bao dữ liêu đầu vào ko đúng:
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_INCORRECT_MAIL, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY_INCORRECT,
                                environment.getProperty("msg.incorrect_mail_message").replace("INCORRECT_MAIL", handleExceptionService.convertIncorrectDataInput(listIncorrectDataUser)));
                    }
                    // bang ket qua thuc hien tien trinh:
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_DIFFICULT, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, result_AllUserAllProgram_ShowMessage);
                    // thông báo kq - CSKK:
                } else if (command.getFileType().equals("update")) { // change shop-code
                    ObjectMapper objectMapper = new ObjectMapper(); // map data từ frontend lên backend
                    List<C2AdminUserAuto> list = objectMapper.readValue(command.getListUserAuto(), new TypeReference<List<C2AdminUserAuto>>() {
                    });
                    Map<String, String> result_AllUserAllProgram_ShowMessage = new HashMap<>();
                    List<String> listIncorrectDataUser = new ArrayList<>(); // Lỗi - dữ liệu đầu vao ko hợp lệ
                    User loginUser = userService.findByUsername();
                    for (C2AdminUserAuto c2AdminUserAuto : list) {
                        System.out.println("USER_NAME:" + c2AdminUserAuto.getUser_name() + "SHOP_CU:" + c2AdminUserAuto.getFull_name()
                                + "SHOP_MOI:" + c2AdminUserAuto.getPhone() + "IP:" + c2AdminUserAuto.getBirthday() +
                                "MAIL:" + c2AdminUserAuto.getCmnd() + "CHUONG TRINH:" + c2AdminUserAuto.getNgaycap());
                        Map<String, Integer> result_OneUserForAllProgram = new HashMap<>();
                        try {
                            if (employeeService.checkUserIsGDV(c2AdminUserAuto.getUser_name()).equals("YES")) { //CHECK GDV
                                if (c2AdminUserAuto.getNgaycap().contains(Constants.PROGRAM_ID_CSKH)) {
                                    HeThongCSKH cskh = new HeThongCSKH(firefoxDriver, seleniumServer);
                                    C2UserAdminDTO dto = new C2UserAdminDTO();
                                    EmployeeDTO employeeDTO = employeeService.findActiveEmp_codeByUsername(c2AdminUserAuto.getUser_name());
                                    if (employeeDTO != null && employeeDTO.getEmp_type() != null) {
                                        dto.setAccount(String.valueOf(employeeDTO.getEmp_type()));
                                    } else {
                                        dto.setAccount("1");
                                    }
                                    dto.setShopCode(c2AdminUserAuto.getPhone());
                                    dto.setUsername(c2AdminUserAuto.getUser_name());
                                    dto.setStatus(String.valueOf(1));
                                    String fullname = c2UserAdminService.findbyUsername(c2AdminUserAuto.getUser_name()).getFullName();
                                    if (fullname != null) {
                                        dto.setFullName(fullname);
                                    } else {
                                        dto.setFullName(employeeService.findEmp_nameByUser(c2AdminUserAuto.getUser_name()));
                                    }
                                    // c2adminuser.account -> loai user
                                    Boolean rsCSKH = cskh.changeShopcode(dto, c2AdminUserAuto.getFull_name(), loginUser);
                                    result_OneUserForAllProgram.put(Constants.PROGRAM_CSKH, rsCSKH == true ? 1 : 0);
                                }
                                if (c2AdminUserAuto.getNgaycap().contains(Constants.PROGRAM_ID_TTCP)) {
                                    C2UserAdminDTO c2UserAdminDTO = new C2UserAdminDTO();
                                    c2UserAdminDTO.setUsername(c2AdminUserAuto.getUser_name());
                                    c2UserAdminDTO.setShopCode(c2AdminUserAuto.getPhone());
                                    c2UserAdminDTO.setStatus("1");
                                    String fullname = c2UserAdminService.findbyUsername(c2AdminUserAuto.getUser_name()).getFullName();
                                    c2UserAdminDTO.setFullName(fullname);
                                    HeThongTTCP ttcp = new HeThongTTCP(firefoxDriver, seleniumServer);
                                    Boolean rsTTCP = ttcp.changeShopcode(c2UserAdminDTO, c2AdminUserAuto.getFull_name(), loginUser);
                                    result_OneUserForAllProgram.put(Constants.PROGRAM_TTCP, rsTTCP == true ? 1 : 0);
                                }
                                if (c2AdminUserAuto.getNgaycap().contains(Constants.PROGRAM_ID_BHTT)) {
                                    HeThongBHTT heThongBHTT = new HeThongBHTT(firefoxDriver, seleniumServer);
                                    Boolean rsBHTT = heThongBHTT.changeShopcode(c2AdminUserAuto.getUser_name(), c2AdminUserAuto.getPhone(), userService.getUserLogin_BHTT_System());
                                    result_OneUserForAllProgram.put(Constants.PROGRAM_BHTT, rsBHTT == true ? 1 : 0);
                                }
                            } else {
                                listIncorrectDataUser.add(environment.getProperty("msg.incorrect_mail_message.wrong_user_AM")
                                        .replace("USER", c2AdminUserAuto.getUser_name()));
                            }
                            if (c2AdminUserAuto.getNgaycap().contains(Constants.PROGRAM_ID_DTHGD)) {
                                QuanTriPhanQuyenUser qtpq = new QuanTriPhanQuyenUser(firefoxDriver, seleniumServer);
                                C2UserAdminDTO dto = new C2UserAdminDTO();
                                dto.setUsername(c2AdminUserAuto.getUser_name());
                                dto.setShopCode(c2AdminUserAuto.getPhone());
                                // tim lai IP cua shop moi (neu user ko nhap IP)
                                if (c2AdminUserAuto.getBirthday() == null || c2AdminUserAuto.getBirthday().trim().equals("")) {
                                    String newGrantedIP = shopCodeIpGrantedService.getIP_Granted_ByShopCode(c2AdminUserAuto.getPhone());
                                    dto.setGranted_ip(newGrantedIP != null ? newGrantedIP : Constants.IP_DEFAULT);
                                    if (newGrantedIP == null) {
                                        listIncorrectDataUser.add(environment.getProperty("msg.incorrect_mail_message.wrong_ip")
                                                .replace("SHOPCODE", c2AdminUserAuto.getPhone()));
                                    }
                                } else {
                                    dto.setGranted_ip(c2AdminUserAuto.getBirthday());
                                }
                                Boolean rsDTHGD = qtpq.changeShopcode(dto, loginUser);
                                result_OneUserForAllProgram.put(Constants.PROGRAM_DTHGD, rsDTHGD == true ? 1 : 0);
                            }
                            if (c2AdminUserAuto.getNgaycap().contains(Constants.PROGRAM_ID_CSKK)) { // CSKK
                                try {
                                    Integer rsCSKK = employeeService.changeShopcodeInCSKK(c2AdminUserAuto.getCmnd(), c2AdminUserAuto.getPhone());
                                    result_OneUserForAllProgram.put(Constants.PROGRAM_CSKK, rsCSKK > 0 ? 1 : 0);
                                } catch (Exception e) {
                                    Throwable cause = e.getCause();
                                    if (cause instanceof GenericJDBCException) { // mail ko tồn tại trên hệ thống
                                        listIncorrectDataUser.add(environment.getProperty("msg.incorrect_mail_message.wrong_mail_shopcode").replace("USER", c2AdminUserAuto.getUser_name()));
                                        result_OneUserForAllProgram.put(Constants.PROGRAM_CSKK, 0);
                                    } else {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                            if (c2AdminUserAuto.getNgaycap().contains(Constants.PROGRAM_ID_RESNUM)) {// RESNUM:
                                // Chuong trinh dang loi - BUG:
                                // UPDATE -> nếu sai user - báo lỗi -> updated row 0
                                //         -> sai shop_code - ko báo lỗi -> update row 1
                                Integer rsResum = employeeService.changeShopCodeInResnum(c2AdminUserAuto.getUser_name(), c2AdminUserAuto.getPhone());
                                result_OneUserForAllProgram.put(Constants.PROGRAM_RESNUM, rsResum > 0 ? 1 : 0);
                            }
                            result_AllUserAllProgram_ShowMessage.put(c2AdminUserAuto.getUser_name(), handleExceptionService.convertResultOneUserForAllProgram(result_OneUserForAllProgram));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!listIncorrectDataUser.isEmpty()) { // Thong bao dữ liêu đầu vào ko đúng:
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_INCORRECT_MAIL, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY_INCORRECT,
                                environment.getProperty("msg.incorrect_mail_message").replace("INCORRECT_MAIL", handleExceptionService.convertIncorrectDataInput(listIncorrectDataUser)));
                    }
                    // ket qua tin trinh`
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_DIFFICULT, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, result_AllUserAllProgram_ShowMessage);
                }
            }
        }
        command.setListResult(addUserList);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    @RequestMapping(value = "/ajax/addDataToTable.html", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<C2AdminUserAuto> addDataToTable(@RequestParam("file") MultipartFile file) throws IOException {
        ExcelReader excelReader = new ExcelReader(file);
        return excelReader.readAddUserExcelFile();
    }

}
