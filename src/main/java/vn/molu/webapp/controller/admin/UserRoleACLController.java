package vn.molu.webapp.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.common.Constants;
import vn.molu.common.utils.RequestUtil;
import vn.molu.domain.admin.User;
import vn.molu.domain.admin.UserRole;
import vn.molu.domain.admin.UserRoleACL;
import vn.molu.service.admin.UserRoleACLService;
import vn.molu.service.admin.UserRoleService;
import vn.molu.service.admin.UserService;
import vn.molu.webapp.command.admin.UserRoleACLCommand;
import vn.molu.webapp.validator.admin.UserRoleACLValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
public class UserRoleACLController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private UserRoleACLService userRoleACLService;
    @Autowired
    private UserService userService;

    final private UserRoleService userRoleService;
    @Autowired
    private UserRoleACLValidator userRoleACLValidator;
    @Autowired
    public UserRoleACLController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @RequestMapping("/admin/user_role_acl.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UserRoleACLCommand command,
                             HttpServletRequest request, BindingResult bindingResult) {
        RequestUtil.initSearchBean(request, command);

        ModelAndView mav = new ModelAndView("admin/user_role_acl/list");
        String crudaction = command.getCrudaction();

        if (command.getPojo().getUser().getUserId() != null){
            User user = userService.findById(command.getPojo().getUser().getUserId());
            command.getPojo().setUser(user);
        }

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)) {
            if (command.getPojo().getUserRoleACLsId() != null) {
                try {
                    userRoleACLService.delete(command.getPojo().getUserRoleACLsId());

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
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)) {
            userRoleACLValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) { // insert case
                try {
                    for (int i = 0; i < command.getCheckList().length; i++){
                        Long userRoleId = Long.valueOf(command.getCheckList()[i]);

                        UserRoleACL userRoleACL = new UserRoleACL();
                        userRoleACL.setUser(command.getPojo().getUser());

                        UserRole userRole = new UserRole();
                        userRole.setUserRoleId(userRoleId);
                        userRoleACL.setUserRole(userRole);

                        userRoleACLService.save(userRoleACL);
                    }

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
            } else {
                mav.addObject(Constants.MESSAGE_VALIDATOR_MODEL_KEY, this.getMessageSourceAccessor().getMessage("errors.select.empty"));
                mav.addObject(Constants.IS_MODAL_SHOW, true);
            }
        }

        List<UserRole> listOfUserRole = null;
        System.out.println(command.getPojo().getUser().getUserGroup() + "==========");
        if (command.getPojo().getUser().getUserGroup() == null){
            listOfUserRole = userRoleService.findAllByUserId(command.getPojo().getUser().getUserId(), Constants.QUERY_NOT_EXISTS);
        } else {
            listOfUserRole = userRoleService.findAllByUserIdAndUserGroupId(command.getPojo().getUser().getUserId(),
                    command.getPojo().getUser().getUserGroup().getUserGroupId(), Constants.QUERY_NOT_EXISTS);
        }

//        List<UserRole> listOfUserRole = userRoleService.findAllByUserIdAndUserGroupId(command.getPojo().getUser().getUserId(), command.getPojo().getUser().getUserGroup().getUserGroupId(), Constants.QUERY_NOT_EXISTS);
        mav.addObject("listOfUserRole", listOfUserRole);

        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(UserRoleACLCommand bean, HttpServletRequest request) {
        List<UserRoleACL> list = userRoleACLService.findByUserId( bean.getPojo().getUser().getUserId());
        bean.setListResult(list);
    }
}
