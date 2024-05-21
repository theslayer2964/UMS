package vn.molu.webapp.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.common.Constants;
import vn.molu.common.utils.RequestUtil;
import vn.molu.dao.temp.ShopDAO;
import vn.molu.domain.admin.ManagerUser;
import vn.molu.domain.admin.ShopCodeIPGranted;
import vn.molu.dto.admin.admin.ShopDTO;
import vn.molu.service.admin.ManagerUserService;
import vn.molu.service.admin.ShopCodeIpGrantedService;
import vn.molu.webapp.command.admin.ShopCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private ShopDAO shopDAO;

    @Autowired
    private ManagerUserService managerUserService;

    @Autowired
    private ShopCodeIpGrantedService shopCodeIpGrantedService;

    @RequestMapping("/list/department.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) ShopCommand command,
                             HttpServletRequest request, BindingResult bindingResult) {
        RequestUtil.initSearchBean(request, command); /// ?????
        ModelAndView mav = new ModelAndView("admin/shop/list");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)) {
            if (!bindingResult.hasErrors()) {
                ShopDTO pojo = command.getPojo();
                if (pojo.getShop_code() == null) { // insert
                    System.out.println("INSERT");
                } else {
                    if (shopCodeIpGrantedService.findByShopcode(pojo.getShop_code()) == null) { // insert
                        try {
                            ShopCodeIPGranted shopGrantedIp = new ShopCodeIPGranted();
                            shopGrantedIp.setShopCode(pojo.getShop_code());
                            shopGrantedIp.setShopType(command.getShop_type());
                            shopGrantedIp.setIpGranted(command.getGrantedIp_shop());
                            ShopDTO shop = shopDAO.findById(pojo.getShop_code());
                            shopGrantedIp.setShopName(shop.getName());
                            shopCodeIpGrantedService.update(shopGrantedIp);

                            mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                            mav.addObject("messageResponse",
                                    this.getMessageSourceAccessor().getMessage("msg.database.update.successful"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                    this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful"));
                            log.error(e.getMessage(), e);
                        }
                    } else { // update
                        try {
                            ShopCodeIPGranted shopGrantedIp = new ShopCodeIPGranted();
                            shopGrantedIp.setShopCode(pojo.getShop_code());
                            shopGrantedIp.setShopType(command.getShop_type());
                            shopGrantedIp.setIpGranted(command.getGrantedIp_shop());
                            System.out.println("UPDATE:" + shopGrantedIp);
                            shopCodeIpGrantedService.updateGrantedIp(shopGrantedIp);
                            mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                            mav.addObject("messageResponse",
                                    this.getMessageSourceAccessor().getMessage("msg.database.update.successful"));
                        } catch (RuntimeException e) {
                            e.printStackTrace();

                            mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                            mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                    this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful"));

                            log.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }
        executeSearch(command);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(ShopCommand command) {
        List<ShopDTO> list = shopDAO.findShopDepartment("2", "1");
        command.setListResult(list);
    }

    @RequestMapping(value = "/ajax/getDepartmentById.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody ShopCodeIPGranted test(@RequestParam("departmentId") String shop_code) {
        try {
            ShopCodeIPGranted shopCodeIPGranted = shopCodeIpGrantedService.findByShopcode(shop_code);
            return shopCodeIPGranted;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ShopCodeIPGranted();
        }
    }

    @RequestMapping(value = "/ajax/getUserByDepartmentId.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<ManagerUser> getManagerUser(@RequestParam("departmentId") String shop_code) {
        try {
            List<ManagerUser> temp = managerUserService.findByShopCode(shop_code);
            List<ManagerUser> list = new ArrayList<>();
            for (ManagerUser user : temp) {
                ManagerUser dto = new ManagerUser();
                dto.setManagerUserId(user.getManagerUserId());
                dto.setUsername(user.getUsername());
                dto.setPosition(user.getPosition());
                dto.setEmail(user.getEmail());
                list.add(dto);
            }
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/ajax/addUser.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody ManagerUser insertManagerUser(@RequestParam("id") Long id,
                                                       @RequestParam("user_name") String user_name,
                                                       @RequestParam("email") String email,
                                                       @RequestParam("position") String position,
                                                       @RequestParam("departmentId") String shop_code) {
        try {
            if (id == null || id.toString().equals("")) { // insert
                System.out.println("in");
                ManagerUser dto = new ManagerUser();
                dto.setShop_code(shop_code);
                dto.setEmail(email);
                dto.setPosition(position);
                dto.setUsername(user_name);
                managerUserService.add(dto);
                return dto;
            } else { // update
                System.out.println("up");
                ManagerUser dto = new ManagerUser();
                dto.setShop_code(shop_code);
                dto.setEmail(email);
                dto.setManagerUserId(id);
                dto.setPosition(position);
                dto.setUsername(user_name);
                managerUserService.add(dto);
                return dto;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/ajax/getMangerUserById.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    ManagerUser getManagerUserById(@RequestParam("managerUserId") Long managerUserId) {
        try {
            ManagerUser entity = managerUserService.findUserById(managerUserId);
            return entity;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ManagerUser();
        }
    }

    @RequestMapping(value = "/ajax/deleteUserbyId.html", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody String deleteMangerUserById(@RequestParam("managerUserId") Long managerUserId) {
        try {
            managerUserService.deleteById(managerUserId);
            return "deleted by manager user id: " + managerUserId;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "Lỗi hệ thống";
        }
    }

    @RequestMapping(value = "/ajax/findgrantedIpByShopcode.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody ShopCodeIPGranted getGrantedIPByShopcode(@RequestParam("shop_code") String shop_code) {
        try {
            ShopCodeIPGranted shopCodeIPGranted = shopCodeIpGrantedService.findByShopcode(shop_code);
            ShopCodeIPGranted mSaleIp = shopCodeIpGrantedService.findByShopcode("mSale");
            if (shopCodeIPGranted.getShopType() == 1) {
                String shopIp = shopCodeIPGranted.getIpGranted();
                String final_ip = mSaleIp.getIpGranted() + ";" + shopIp;
                shopCodeIPGranted.setIpGranted(final_ip);
            } else if (shopCodeIPGranted.getShopType() == 2) {
                shopCodeIPGranted.setIpGranted(mSaleIp.getIpGranted());
            }
            return shopCodeIPGranted;
        } catch (Exception e) {
            return new ShopCodeIPGranted();
        }
    }
}
