package vn.molu.webapp.controller.admin;

import org.apache.commons.lang.StringUtils;
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
import vn.molu.dao.temp.*;
import vn.molu.domain.admin.GroupUser;
import vn.molu.domain.admin.Program;
import vn.molu.domain.admin.User;
import vn.molu.dto.admin.admin.*;
import vn.molu.service.admin.*;
import vn.molu.service.exception.HandleExceptionService;
import vn.molu.webapp.command.admin.AddUserCommand;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AddUserController extends ApplicationObjectSupport {

    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private ProgramService programService;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private CollectionGroupDAO collectionGroupDAO;
    @Autowired
    private AdminAccessTimeDAO adminAccessTimeDAO;
    @Autowired
    private ExplanationFormService explanationFormService;
    @Autowired
    private AutoC2UserService autoC2UserService;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private CSKK_UserService cskkUserService;
    @Autowired
    private Resnum_UserService resnumUserService;
    @Autowired
    private C2UserAdminDAO c2UserAdminDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private HandleExceptionService handleExceptionService;
    @Autowired
    private Environment environment;
    @Value("${system.selenium-server.path}")
    private String seleniumServer;
    @Value("${system.firefox.driver}")
    private String firefoxDriver;

    @RequestMapping("/list/add_user_c2.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) AddUserCommand command,
                             HttpServletRequest request, BindingResult bindingResult) throws InterruptedException, ParseException, IOException {
        RequestUtil.initSearchBean(request, command);
        ModelAndView mav = new ModelAndView("admin/addC2user/insert");
        String crudaction = command.getCrudaction();
        List<Program> programList = programService.findAll();
        mav.addObject("listProgram", programList);
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)) {
            createUserAutomatic(mav, command);
            updateForm(command);
            autoC2UserService.save(command.getPojo());
        }

        mav.addObject(Constants.LIST_MODEL_KEY, command);
        List<ShopDTO> listShop = shopDAO.findActiveShopCenCode2("2", "1");
        mav.addObject("listShop", listShop);
        List<CityDTO> cities = cityDAO.findCity();
        mav.addObject("cities", cities);
        List<AdminAccessTimeDTO> times = adminAccessTimeDAO.findAll();
        mav.addObject("times", times);
        List<CollectionGroupDTO> groups = collectionGroupDAO.findActiveGroupCollection("1");
        mav.addObject("groups", groups);
        return mav;
    }

    private void updateForm(AddUserCommand command) throws ParseException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(command.getUploadDate());
        String username = command.getPojo().getUser_name();
        String form_type = command.getForm_type();
        explanationFormService.uploadFile(command.getFile(), date, username, form_type);
    }

    private void createUserAutomatic(ModelAndView mav, AddUserCommand command) throws ParseException, InterruptedException {
        String[] programs = command.getPojo().getProgram().split("//|");
        Map<String, String> result_AllUserAllProgram_ShowMessage = new HashMap<>(); // lay kq User
        List<String> listIncorrectDataUser = new ArrayList<>(); // Lỗi - dữ liệu đầu vao ko hợp lệ
        User userLogin = userService.findByUsername();
        for (String string : programs) {
            Map<String, Integer> result_OneUserForAllProgram = new HashMap<>();
            if (string.equals(Constants.PROGRAM_ID_DTHGD)) {
                List<GroupUser> listGroupUserPermission = groupUserService.findAll();
                QuanTriPhanQuyenUser quanTriPhanQuyenUser = new QuanTriPhanQuyenUser(firefoxDriver, seleniumServer);
                boolean rsDTHGD = quanTriPhanQuyenUser.createUser(command.getPojo(), listGroupUserPermission, userLogin); // birthday='2024-02-06'
                result_OneUserForAllProgram.put(Constants.PROGRAM_DTHGD, rsDTHGD == true ? 1 : 0);
            } else if (string.equals(Constants.PROGRAM_ID_TTCP)) {
                HeThongTTCP heThongTTCP = new HeThongTTCP(firefoxDriver, seleniumServer);
                boolean rsTTCP = heThongTTCP.createUser(command.getPojo(), userLogin); // birthday='2024-02-06', ngaycap='2024-02-13'
                result_OneUserForAllProgram.put(Constants.PROGRAM_TTCP, rsTTCP == true ? 1 : 0);
            } else if (string.equals(Constants.PROGRAM_ID_CSKH)) {
                HeThongCSKH heThongCSKH = new HeThongCSKH(firefoxDriver, seleniumServer);
                String city = cityDAO.getCityname(command.getPojo().getCity()).get(0).getCity_name();
                boolean rsCSKH = heThongCSKH.createUser(command.getPojo(), city, userLogin); // birthday='2024-02-06', ngaycap='2024-02-13'
                result_OneUserForAllProgram.put(Constants.PROGRAM_CSKH, rsCSKH == true ? 1 : 0);
            } else if (string.equals(Constants.PROGRAM_ID_CSKK)) {
                CSKK_UserDTO cskkUserDTO = new CSKK_UserDTO();
                cskkUserDTO.setUsername(command.getPojo().getUser_name());
                cskkUserDTO.setEmail(command.getEmail());
                cskkUserDTO.setFullName(command.getPojo().getFull_name());
                cskkUserDTO.setShopCode(command.getPojo().getShop_code());
                cskkUserDTO.setPhone(command.getPojo().getPhone());
                ShopDTO shopDTO = shopDAO.findById(command.getPojo().getShop_code());
                cskkUserDTO.setShopName(shopDTO.getName());
                cskkUserDTO.setStatus(Constants.CSKK_Status_0);
                cskkUserDTO.setPosition(command.getChucdanh());
                if (command.getPojo().getPosition().equals("AM")) // am
                    cskkUserDTO.setBranch(command.getPojo().getToThu());
                else if (command.getPojo().getPosition().equals("GDV") || command.getPojo().getPosition().equals("CHT")) {// gdv
                    cskkUserDTO.setBranch(command.getPojo().getShop_code());
                } else { // dlc
                    cskkUserDTO.setBranch(command.getPojo().getToThu());
                    cskkUserDTO.setShopCode(command.getPojo().getToThu());
                }
                // b1: delete temp
                List<CSKK_UserDTO> tempSize = cskkUserService.getListCSKK_DTO();
                if (!tempSize.isEmpty()) {
                    cskkUserService.deleteTemp();
                }
                // b2: add new user + execute procedure
                cskkUserService.insert(cskkUserDTO);
                CSKK_UserDTO rsCSKK = cskkUserService.findCSKK_UserByUsername(command.getPojo().getUser_name());
                result_OneUserForAllProgram.put(Constants.PROGRAM_CSKK, rsCSKK.getStatus() == 1 ? 1 : 0);
                // b3: show kq:
            } else if(string.equals(Constants.PROGRAM_ID_BHTT)){
                HeThongBHTT heThongBHTT = new HeThongBHTT(firefoxDriver, seleniumServer);
                List<GroupUser> listGroupUserPermission = groupUserService.findGroupUserByRoleNProgram(0, Constants.PROGRAM_ID_BHTT);
                Boolean rsBHTT = heThongBHTT.createUser(command.getPojo(), userService.getUserLogin_BHTT_System(), listGroupUserPermission);
                result_OneUserForAllProgram.put(Constants.PROGRAM_BHTT, rsBHTT == true ? 1 : 0);
            } else if (string.equals(Constants.PROGRAM_ID_RESNUM)) {
                Resum_UserDTO resnum = new Resum_UserDTO();
                resnum.setUser_tracuu(command.getEmail().split("@")[0]);
                Integer rsResum = resnumUserService.insert(resnum);
                result_OneUserForAllProgram.put(Constants.PROGRAM_RESNUM, rsResum > 0 ? 1 : 0);
            }
            result_AllUserAllProgram_ShowMessage.put(command.getPojo().getUser_name(), handleExceptionService.convertResultOneUserForAllProgram(result_OneUserForAllProgram));
        }
        if (!listIncorrectDataUser.isEmpty()) { // Thong bao dữ liêu đầu vào ko đúng:
            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_INCORRECT_MAIL, true);
            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY_INCORRECT,
                    environment.getProperty("msg.incorrect_mail_message").replace("INCORRECT_MAIL", handleExceptionService.convertIncorrectDataInput(listIncorrectDataUser)));
        }
        // bang ket qua thuc hien tien trinh:
        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY_DIFFICULT, true);
        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, result_AllUserAllProgram_ShowMessage);
    }

    @RequestMapping(value = "/ajax/getNamByCityname.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<CityDTO> getCitybyId(@RequestParam("city") String city) {
        try {
            List<CityDTO> areas = cityDAO.getCitybyId(city);
            return areas;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/ajax/checkUserExist.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody boolean checkuserExist(@RequestParam("user_name") String user_name) {
        try {
            System.out.println("user_name:" + user_name);
            if (c2UserAdminDAO.findByUsername(user_name.toUpperCase()) != null && c2UserAdminDAO.findByUsername(user_name.toUpperCase()).getUsername() != null)
                return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
