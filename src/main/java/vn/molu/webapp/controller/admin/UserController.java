package vn.molu.webapp.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.common.Constants;
import vn.molu.common.utils.DesEncrypterUtils;
import vn.molu.common.utils.RequestUtil;
import vn.molu.common.utils.SecurityUtils;
import vn.molu.domain.admin.User;
import vn.molu.domain.admin.UserGroup;
import vn.molu.service.admin.UserGroupService;
import vn.molu.service.admin.UserService;
import vn.molu.webapp.command.admin.C2AdminUserCommand;
import vn.molu.webapp.command.admin.UserCommand;
import vn.molu.webapp.validator.admin.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserGroupService userGroupService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("/admin/user-list.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UserCommand command,
                             HttpServletRequest request, BindingResult bindingResult){
        RequestUtil.initSearchBean(request, command); /// ?????
        ModelAndView mav = new ModelAndView("admin/user/list");
        String crudaction = command.getCrudaction();

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)) { // delete
            // action
            if (command.getPojo().getUserId() != null) {
                try {
                    userService.delete(command.getPojo().getUserId());

                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                            this.getMessageSourceAccessor().getMessage("msg.database.delete.successful"));
                } catch (Exception e) {

                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                            this.getMessageSourceAccessor().getMessage("msg.database.delete.unsuccessful"));

                    log.error(e.getMessage(), e);
                }
            }
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)) { // update
            // or
            // insert
            // action
            userValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) {
                User pojo = command.getPojo();
                if (pojo.getUserId() == null) { // insert case
                    try {
                        Timestamp currentTime = new Timestamp(new Date().getTime());
                        pojo.setCreatedDate(currentTime);
                        pojo.setModifiedDate(currentTime);
                        pojo.setFlgDelete("1");
                        //pojo.setAccountType(1);
                        pojo.setUserName(pojo.getUserName().toLowerCase());
                        pojo.setPassword(DesEncrypterUtils.getInstance().encrypt(pojo.getPassword()));
                        if (pojo.getUserGroup().getUserGroupId() == -1L) {
                            pojo.setUserGroup(null);
                        }

                        pojo = userService.save(pojo);

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                this.getMessageSourceAccessor().getMessage("msg.database.add.successful"));
                    } catch (RuntimeException e) {

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                this.getMessageSourceAccessor().getMessage("msg.database.add.unsuccessful"));

                        log.error(e.getMessage(), e);
                    }
                }
                else { // update case
                    try {
                        User tempUserGroup = userService.findById(pojo.getUserId());
                        pojo.setCreatedDate(tempUserGroup.getCreatedDate());
                        pojo.setFlgDelete(tempUserGroup.getFlgDelete());
                        //pojo.setAccountType(tempUserGroup.getAccountType());

                        Timestamp currentTime = new Timestamp(new Date().getTime());
                        pojo.setModifiedDate(currentTime);
                        pojo.setPassword(DesEncrypterUtils.getInstance().encrypt((pojo.getPassword())));
                        if (pojo.getUserGroup().getUserGroupId() == -1L) {
                            pojo.setUserGroup(null);
                        }
                        pojo = userService.update(pojo);

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
                // reset modal form
                command.setPojo(null);
            } else {
                mav.addObject(Constants.IS_MODAL_SHOW, true);
            }
        } if(StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_REGISTER)){
            // validate
            if (!bindingResult.hasErrors()) {
                System.out.println("XXXX:" + command.getPojo().toString());
            }
        }

        executeSearch(command);
        List<UserGroup> listOfUserGroup = userGroupService.findAll();
        mav.addObject("listOfUserGroup", listOfUserGroup);

        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }
    private void executeSearch(UserCommand bean) {
        Map<String, Object> props = new HashMap<>();
        if (StringUtils.isNotBlank(bean.getDisplayName())) {
            props.put("displayName", bean.getDisplayName());
        }
        if (bean.getAccountType() != null) {
            System.out.println(bean.getAccountType());
            if (bean.getAccountType() != -1) {
                props.put("accountType", bean.getAccountType());
            }
        }

        if (bean.getUserGroup() != null) {
            if (bean.getUserGroup().getUserGroupId() != -1) {
                props.put("userGroup.userGroupId", bean.getUserGroup().getUserGroupId());
            }
        }
        List<User> list;
        if (props.size() == 0) {
            list = userService.findAll();
        } else {
            System.out.println("USER SEARCH: " + bean.getUserGroup().getUserGroupId() + " : "
                    + bean.getDisplayName() + " : " + bean.getAccountType());
            list = userService.findUser(props);
        }

        for (User user : list) {
            user.setPassword(DesEncrypterUtils.getInstance().decrypt(user.getPassword()));
        }
        bean.setListResult(list);
    }
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    User getUserById(@PathVariable("userId") Long userId) {
        try {
            System.out.println("--- Rest ---- getUserById----");
            return userService.findById(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new User();
        }
    }

    @RequestMapping(value = "/ajax/getUserById.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    User test(@RequestParam("userId") Long userId) {
        try {
            User entity = userService.findById(userId);
            if (entity != null) {
                entity.setPassword(DesEncrypterUtils.getInstance().decrypt(entity.getPassword()));
            }

            return entity;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new User();
        }
    }

    @RequestMapping("/profile.html")
    public ModelAndView profile(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UserCommand command,
                                HttpServletRequest request, BindingResult bindingResult) {
        RequestUtil.initSearchBean(request, command);

        ModelAndView mav = new ModelAndView("admin/profile");
        String crudaction = command.getCrudaction();

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)) {
            userValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) {
                User pojo = command.getPojo();
                try {
                    User tempUserGroup = userService.findById(pojo.getUserId());
                    pojo.setCreatedDate(tempUserGroup.getCreatedDate());
                    pojo.setFlgDelete(tempUserGroup.getFlgDelete());
                    pojo.setUserGroup(tempUserGroup.getUserGroup());
                    pojo.setAccountType(tempUserGroup.getAccountType());

                    Timestamp currentTime = new Timestamp(new Date().getTime());
                    pojo.setModifiedDate(currentTime);
                    pojo.setPassword(DesEncrypterUtils.getInstance().encrypt((pojo.getPassword())));

                    pojo = userService.update(pojo);

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
        User userEntity = userService.findById(SecurityUtils.getLoginUserId());
        userEntity.setPassword(DesEncrypterUtils.getInstance().decrypt((userEntity.getPassword())));
        System.out.println("aaaaaaaaaaa=" + userEntity.getPassword());
        command.setPojo(userEntity);
        return mav;
    }

    @RequestMapping("/admin/register.html")
    public ModelAndView list2(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UserCommand command,
                              HttpServletRequest request, BindingResult bindingResult){
        RequestUtil.initSearchBean(request, command);
        ModelAndView mav = new ModelAndView("login");
        String crudaction = command.getCrudaction();
        User user = command.getPojo();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_REGISTER)){
            // validate
//            if (!bindingResult.hasErrors()) {
                System.out.println("XXXX:" + user.toString());
//            }
        }
        return mav;
    }
}
