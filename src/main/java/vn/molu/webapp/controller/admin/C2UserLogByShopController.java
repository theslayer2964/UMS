package vn.molu.webapp.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import vn.molu.dao.temp.ViewLogUser_ShopDAO;
import vn.molu.dao.temp.ViewStatisticalLogShopDAO;
import vn.molu.dto.admin.admin.*;
import vn.molu.webapp.command.admin.ViewStatisticalLogShopCommand;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class C2UserLogByShopController {
    private transient final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ViewStatisticalLogShopDAO viewDAO;
    // VALIDATE

    // VALIDATE

    @Autowired
    private ShopDAO shopDAO;

    @Autowired
    private C2UserAdminDAO c2UserAdminDAO;

    @Autowired
    private ViewLogUser_ShopDAO viewLogUser_shopDAO;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd-MMM-yy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("/utilities/user_c2_log_by_shop.html")
    public ModelAndView listC2UserLogByShop(@ModelAttribute(value = Constants.FORM_MODEL_KEY) ViewStatisticalLogShopCommand command,
                                            HttpServletRequest request, BindingResult bindingResult) {
        RequestUtil.initSearchBean(request, command); // chua hiu lam`z`???
        ModelAndView mav = new ModelAndView("admin/utilities/shop/listLogByShop");
        String crudaction = command.getCrudaction();
        // CRUD

//        executeSearch(command, mav);
        List<ShopDTO> listShop = shopDAO.findActiveShopCenCode2("2", "1");
        mav.addObject("listShop", listShop);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;

    }

//    private void executeSearch(ViewStatisticalLogShopCommand command, ModelAndView mav) {
//        /// search:
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String searchFrom =
//                command.getFrom() == null || command.getFrom().trim().equals("") ? formatter.format(LocalDate.now().minusDays(1)).toString() : command.getFrom();
//        String searchTo =
//                command.getTo() == null || command.getTo().trim().equals("") ? formatter.format(LocalDate.now()).toString() : command.getTo();
//        String searchShopCode = command.getShopCode();
//        List<ViewStatisticalLogShopDTO> list = new ArrayList<>();
//        if (StringUtils.isBlank(searchShopCode) || searchShopCode.equals("-1")) {
//            list = viewDAO.layDS_TraCuu_Log_TheoShop(searchFrom, searchTo);
//        } else { // search
//            List<C2UserAdminDTO> listUser = c2UserAdminDAO.findUserByShop(searchShopCode);
//            if (listUser.size() <= 1000) {
//                String user_joined_string = joinUsername(listUser);
//                list = viewDAO.layDS_TraCuu_Log_TheoShop_All(searchFrom, searchTo, searchShopCode, user_joined_string);
//            } else {
//                int line = listUser.size();
//                int soDongMaximumOracleXuLy = 999;
//                while (line >= 0) {
//                    if (line > 999) {
//                        List<C2UserAdminDTO> temp = listUser.subList(0, soDongMaximumOracleXuLy);
//                        String user_joined_string = joinUsername(temp);
//                        List<ViewStatisticalLogShopDTO> listUserShopTemp = viewDAO.layDS_TraCuu_Log_TheoShop_All(searchFrom, searchTo, searchShopCode, user_joined_string);
//                        list.addAll(listUserShopTemp);
//                        listUser = listUser.subList(soDongMaximumOracleXuLy, line - 1);
//                    } else {
//                        System.out.println("tren 1000: " + line);
//                        List<C2UserAdminDTO> temp = listUser.subList(0, line - 1);
//                        String user_joined_string = joinUsername(temp);
//                        List<ViewStatisticalLogShopDTO> listUserShopTemp = viewDAO.layDS_TraCuu_Log_TheoShop_All(searchFrom, searchTo, searchShopCode, user_joined_string);
//                        list.addAll(listUserShopTemp);
//                    }
//                    line -= soDongMaximumOracleXuLy;
//                }
//            }
//        }
//        command.setListResult(list);
//        mav.addObject(Constants.FROM_DATE, searchFrom);
//        mav.addObject(Constants.TO_DATE, searchTo);
//    }

    @RequestMapping(value = "/admin/statistical_log_shop.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<ViewStatisticalLogShopDTO> getShopUser(@RequestParam("from") String from,
                                                                     @RequestParam("to") String to,
                                                                     @RequestParam("shop_code") String shop_code) {
        System.out.println(from + to + shop_code);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String searchFrom =
                from == null || from.trim().equals("") ? formatter.format(LocalDate.now().minusDays(1)).toString() : from;
        String searchTo =
                to == null || to.trim().equals("") ? formatter.format(LocalDate.now()).toString() : to;
        List<ViewStatisticalLogShopDTO> list;
        if (StringUtils.isBlank(shop_code) || shop_code.equals("-1")) {
            list = viewDAO.layDS_TraCuu_Log_TheoShop(searchFrom, searchTo);
        } else {
            List<C2UserAdminDTO> listUser = c2UserAdminDAO.findUserByShop(shop_code);
            if (listUser.size() <= 1000) {
                String user_joined_string = joinUsername(listUser);
                list = viewDAO.layDS_TraCuu_Log_TheoShop_All(searchFrom, searchTo, shop_code, user_joined_string);
            } else {
                int line = listUser.size();
                List<ViewStatisticalLogShopDTO> listTemp = new ArrayList<>();
                int soDongMaximumOracleXuLy = 999;
                while (line >= 0) {
                    if (line > 999) {
                        List<C2UserAdminDTO> temp = listUser.subList(0, soDongMaximumOracleXuLy);
                        String user_joined_string = joinUsername(temp);
                        List<ViewStatisticalLogShopDTO> listUserShopTemp = viewDAO.layDS_TraCuu_Log_TheoShop_All(searchFrom, searchTo, shop_code, user_joined_string);
                        listTemp.addAll(listUserShopTemp);
                        listUser = listUser.subList(soDongMaximumOracleXuLy, line - 1);
                    } else {
                        List<C2UserAdminDTO> temp = listUser.subList(0, line - 1);
                        String user_joined_string = joinUsername(temp);
                        List<ViewStatisticalLogShopDTO> listUserShopTemp = viewDAO.layDS_TraCuu_Log_TheoShop_All(searchFrom, searchTo, shop_code, user_joined_string);
                        listTemp.addAll(listUserShopTemp);
                    }
                    line -= soDongMaximumOracleXuLy;
                }
                list = summaryResultList(listTemp);
            }
        }
        return list;
    }

    @RequestMapping(value = "/ajax/getViewLogUserDetail_Shop.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    List<ViewLogUser_ShopDTO> test(@RequestParam("shop_code") String shop_code,
                                   @RequestParam("from") String from,
                                   @RequestParam("to") String to) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String searchFrom =
                    from == null || from.trim().equals("") ? formatter.format(LocalDate.now().minusDays(1)).toString() : from;
            String searchTo =
                    to == null || to.trim().equals("") ? formatter.format(LocalDate.now()).toString() : to;
            List<C2UserAdminDTO> listUser = c2UserAdminDAO.findUserByShop(shop_code);
            List<ViewLogUser_ShopDTO> kq = new ArrayList<>();
            if (listUser.size() <= 1000) {
                String user_joined_string = joinUsername(listUser);
                kq = viewLogUser_shopDAO.layDS_UserByShop_ThongKe(user_joined_string, searchFrom, searchTo);
            } else {
                int line = listUser.size();
                int soDongMaximumOracleXuLy = 999;
                while (line >= 0) { // 99
                    if (line > 999) {
                        List<C2UserAdminDTO> temp = listUser.subList(0, soDongMaximumOracleXuLy);
                        String user_joined_string = joinUsername(temp);
                        List<ViewLogUser_ShopDTO> listUserShopTemp = viewLogUser_shopDAO.layDS_UserByShop_ThongKe(user_joined_string, searchFrom, searchTo);
                        kq.addAll(listUserShopTemp);
                        listUser = listUser.subList(soDongMaximumOracleXuLy, line - 1);
                    } else {
                        List<C2UserAdminDTO> temp = listUser.subList(0, line - 1);
                        String user_joined_string = joinUsername(temp);
                        List<ViewLogUser_ShopDTO> listUserShopTemp = viewLogUser_shopDAO.layDS_UserByShop_ThongKe(user_joined_string, searchFrom, searchTo);
                        kq.addAll(listUserShopTemp);
                    }
                    line -= soDongMaximumOracleXuLy;
                }
            }
            return kq;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private List<ViewStatisticalLogShopDTO> summaryResultList(List<ViewStatisticalLogShopDTO> list) {
        List<ViewStatisticalLogShopDTO> rs = new ArrayList<>();
        long sum = 0;
        for (ViewStatisticalLogShopDTO dto : list) {
            sum += dto.getSoLuong();
        }
        list.get(0).setSoLuong(sum);
        rs.add(list.get(0));
        return rs;
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
}
