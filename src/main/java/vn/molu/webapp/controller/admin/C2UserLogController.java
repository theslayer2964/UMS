package vn.molu.webapp.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.common.Constants;
import vn.molu.common.utils.RequestUtil;
import vn.molu.dao.temp.C2UserAdminDAO;
import vn.molu.dao.temp.ShopDAO;
import vn.molu.dao.temp.ViewLogUser_IP_DAO;
import vn.molu.dao.temp.ViewStatisticalLogUserDAO;
import vn.molu.dto.admin.admin.*;
import vn.molu.webapp.command.admin.ViewStatisticalLogUserCommand;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class C2UserLogController {
    // VALIDATE
    // VALIDATE
    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private ViewStatisticalLogUserDAO userDAO;
    @Autowired
    private ViewLogUser_IP_DAO viewLogUserIpDao;
    @Autowired
    private C2UserAdminDAO c2UserAdminDAO;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd-MMM-yy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("/utilities/user_c2_log.html")
    public ModelAndView listC2UserLog(@ModelAttribute(value = Constants.FORM_MODEL_KEY) ViewStatisticalLogUserCommand command,
                                      HttpServletRequest request, BindingResult bindingResult) throws Exception {
        RequestUtil.initSearchBean(request, command); // chua hiu lam`z`???
        ModelAndView mav = new ModelAndView("admin/utilities/user/listLog");
        List<ShopDTO> listShop = shopDAO.findActiveShopCenCode2("2", "1");
        mav.addObject("listShop", listShop);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }


    @RequestMapping(value = "/admin/statistical_log_user.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<ViewStatisticalLogUserDTO> getShopUser(@RequestParam("from") String from,
                                                                     @RequestParam("to") String to,
                                                                     @RequestParam("shop_code") String shop_code) {
        System.out.println(from + to + shop_code);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String searchFrom =
                from == null || from.trim().equals("") ? formatter.format(LocalDate.now().minusDays(1)).toString() : from;
        String searchTo =
                to == null || to.trim().equals("") ? formatter.format(LocalDate.now()).toString() : to;
        List<ViewStatisticalLogUserDTO> list;
        if (StringUtils.isBlank(shop_code) || shop_code.equals("-1")) { // load
            list = userDAO.layDS_TraCuuUser_Log(searchFrom, searchTo);
        } else { // search
            List<C2UserAdminDTO> listUser = c2UserAdminDAO.findUserByShop(shop_code);
            if (listUser.size() <= 1000) {
                String user_joined_string = joinUsername(listUser);
                list = userDAO.layDS_TraCuuUser_Log_All(searchFrom, searchTo, shop_code, user_joined_string);
            } else {  // tren 1000
                int line = listUser.size();
                List<ViewStatisticalLogUserDTO> listTemp = new ArrayList<>();
                int soDongMaxiumOracleXuLy = 999;
                while (line >= 0) {
                    if (line > 999) {
                        List<C2UserAdminDTO> temp = listUser.subList(0, soDongMaxiumOracleXuLy);
                        String user_joined_string = joinUsername(temp);
                        List<ViewStatisticalLogUserDTO> listUserTemp = userDAO.layDS_TraCuuUser_Log_All(searchFrom, searchTo, shop_code, user_joined_string);
                        listTemp.addAll(listUserTemp);
                        listUser = listUser.subList(soDongMaxiumOracleXuLy, line - 1);
                    } else {
                        List<C2UserAdminDTO> temp = listUser.subList(0, line - 1);
                        String user_joined_string = joinUsername(temp);
                        List<ViewStatisticalLogUserDTO> listUserTemp = userDAO.layDS_TraCuuUser_Log_All(searchFrom, searchTo, shop_code, user_joined_string);
                        listTemp.addAll(listUserTemp);
                    }
                    line -= soDongMaxiumOracleXuLy;
                }
                list = listTemp;
            }
        }
        return list;
    }

    private String joinUsername(List<C2UserAdminDTO> listUser) {
        String rs = "";
        for (C2UserAdminDTO user : listUser) {
            String temp = "'" + user.getUsername() + "',";
            rs += temp;
        }
        rs += "'null'";
        return rs;
    }

    @RequestMapping(value = "/ajax/getViewLogIpDetail_User.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    List<ViewLogUser_IP_DTO> test(@RequestParam("user_name") String user_name,
                                  @RequestParam("from") String from,
                                  @RequestParam("to") String to) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String searchFrom =
                    from == null || from.trim().equals("") ? formatter.format(LocalDate.now().minusDays(1)).toString() : from;
            String searchTo =
                    to == null || to.trim().equals("") ? formatter.format(LocalDate.now()).toString() : to;
            List<ViewLogUser_IP_DTO> kq = viewLogUserIpDao.layDS_IP_Truycap(user_name, searchFrom, searchTo);
            return kq;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
