package vn.molu.webapp.controller.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
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
import vn.molu.common.utils.DesEncrypterUtils;
import vn.molu.common.utils.RequestUtil;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.domain.admin.User;
import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.service.admin.CSKK_UserService;
import vn.molu.service.admin.Resnum_UserService;
import vn.molu.service.admin.UserService;
import vn.molu.service.excel.ExcelReader;
import vn.molu.service.exception.HandleExceptionService;
import vn.molu.webapp.command.admin.ManagerUserByFileCommand;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ManagerUserByFileController extends ApplicationObjectSupport {
    @Autowired
    private UserService userService;
    @Autowired
    private CSKK_UserService cskkUserService;
    @Autowired
    private Resnum_UserService resnumUserService;
    @Autowired
    private HandleExceptionService handleExceptionService;
    @Autowired
    private Environment environment;
    @Value("${system.chrome.path}")
    private String chromeLocationUrl;

    @RequestMapping("/list/manager_user_c2_file.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) ManagerUserByFileCommand command,
                             HttpServletRequest request, BindingResult bindingResult) throws IOException, InterruptedException {
        RequestUtil.initSearchBean(request, command);
        ModelAndView mav = new ModelAndView("admin/addC2user/managerUserWithFile");
        String crudaction = command.getCrudaction();
        List<String> inactivityUserList = new ArrayList<>();

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INACTIVITY)) {
            if (!bindingResult.hasErrors()) {
                if (!command.getFile().isEmpty()) {
                    ExcelReader excelReader = new ExcelReader(command.getFile());
                    inactivityUserList = excelReader.readInactivityExcelFile();
                }
            }
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.SAVE_MANY_USER_BY_FILE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<C2AdminUserAuto> list = objectMapper.readValue(command.getListRetire(), new TypeReference<List<C2AdminUserAuto>>() {
            });
            User loginUser = userService.findByUsername(); // USER LOGIN
            Map<String, String> result_AllUserAllProgram_ShowMessage = new HashMap<>(); // xuat KQ
            List<String> listIncorrectDataUser = new ArrayList<>(); // Lỗi - dữ liệu đầu vao ko hợp lệ
            list.forEach(user -> {
                System.out.println("XXX: " + user);
                Map<String, Integer> result_OneUserForAllProgram = new HashMap<>();
                try {
                    if (user.getProgram().contains("3")) { // cskh
                        HeThongCSKH cskh = new HeThongCSKH(chromeLocationUrl);
                        C2UserAdminDTO dto = new C2UserAdminDTO();
                        dto.setUsername(user.getUser_name());
                        dto.setFullName("TEST");
                        boolean rsCSKH = cskh.inactiveUser(dto, user.getShop_code(), loginUser);
                        result_OneUserForAllProgram.put(Constants.PROGRAM_CSKH, rsCSKH == true ? 1 : 0);
                    }
                    if (user.getProgram().contains("2")) { // ttcp
                        HeThongTTCP ttcp = new HeThongTTCP(chromeLocationUrl);
                        C2UserAdminDTO dto = new C2UserAdminDTO();
                        dto.setUsername(user.getUser_name());
                        boolean rsTTCP = ttcp.inactiveUser(dto, user.getShop_code(), loginUser);
                        result_OneUserForAllProgram.put(Constants.PROGRAM_TTCP, rsTTCP == true ? 1 : 0);
                    }
                    if (user.getProgram().contains("1")) {
                        QuanTriPhanQuyenUser quanTriPhanQuyenUser = new QuanTriPhanQuyenUser(chromeLocationUrl);
                        boolean rsDTHGD = quanTriPhanQuyenUser.inactivityUser(user.getUser_name(), loginUser);
                        result_OneUserForAllProgram.put(Constants.PROGRAM_DTHGD, rsDTHGD == true ? 1 : 0);
                    }
                    if (user.getProgram().contains("4")) { // cskk
                        Integer rsCSKK = cskkUserService.lockUser(user.getEmail());
                        result_OneUserForAllProgram.put(Constants.PROGRAM_CSKK, rsCSKK > 0 ? 1 : 0);
                    }
                    if (user.getProgram().contains("5")) { // resnum
                        Integer rsResnum = resnumUserService.lockUser(user.getUser_name());
                        result_OneUserForAllProgram.put(Constants.PROGRAM_RESNUM, rsResnum > 0 ? 1 : 0);
                    }
                    if (user.getProgram().contains("6")) {
                        HeThongBHTT heThongBHTT = new HeThongBHTT(chromeLocationUrl);
                        Boolean rsBHTT = heThongBHTT.inactiveUser(user.getUser_name(), userService.getUserLogin_BHTT_System());
                        result_OneUserForAllProgram.put(Constants.PROGRAM_BHTT, rsBHTT == true ? 1 : 0);
                    }
                    result_AllUserAllProgram_ShowMessage.put(user.getUser_name(), handleExceptionService.convertResultOneUserForAllProgram(result_OneUserForAllProgram));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
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
        command.setListResult(inactivityUserList);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    @RequestMapping(value = "/ajax/addUserToTable.html", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<String> addDataToTable(@RequestParam("file") MultipartFile file) throws IOException {
        ExcelReader excelReader = new ExcelReader(file);
        return excelReader.readInactivityExcelFile();
    }
}
