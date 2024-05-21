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
import vn.molu.domain.admin.UserGroup;
import vn.molu.domain.admin.UserGroupRoleACL;
import vn.molu.domain.admin.UserRole;
import vn.molu.service.admin.UserGroupRoleACLService;
import vn.molu.service.admin.UserGroupService;
import vn.molu.service.admin.UserRoleService;
import vn.molu.webapp.command.admin.UserGroupRoleACLCommand;
import vn.molu.webapp.validator.admin.UserGroupRoleACLValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserGroupRoleACLController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserGroupRoleACLService userGroupRoleACLService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserGroupRoleACLValidator userGroupRoleACLValidator;

    @RequestMapping("/admin/group_role_acl.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UserGroupRoleACLCommand command,
                             HttpServletRequest request, BindingResult bindingResult) {
        RequestUtil.initSearchBean(request, command);

        ModelAndView mav = new ModelAndView("admin/group_role_acl/list");
        String crudaction = command.getCrudaction();

        if (command.getPojo().getUserGroup() != null) {
            UserGroup userGroup = userGroupService.findById(command.getPojo().getUserGroup().getUserGroupId());
            command.getPojo().setUserGroup(userGroup);
        }

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)) { // delete
            // action
            if (command.getPojo().getUserGroupRoleACLId() != null) {
                try {
                    userGroupRoleACLService.delete(command.getPojo().getUserGroupRoleACLId());

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
            userGroupRoleACLValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) { // insert case
                try {
                    for (int i = 0; i < command.getCheckList().length; i++) {
                        Long userRoleId = Long.valueOf(command.getCheckList()[i]);

                        UserGroupRoleACL userGroupRoleACL = new UserGroupRoleACL();
                        userGroupRoleACL.setUserGroup(command.getPojo().getUserGroup());

                        UserRole userRole = new UserRole();
                        userRole.setUserRoleId(userRoleId);
                        userGroupRoleACL.setUserRole(userRole);

                        userGroupRoleACLService.save(userGroupRoleACL);
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
                mav.addObject(Constants.MESSAGE_VALIDATOR_MODEL_KEY,
                        this.getMessageSourceAccessor().getMessage("errors.select.empty"));
                mav.addObject(Constants.IS_MODAL_SHOW, true);
            }
        }

        List<UserRole> listOfUserRole = userRoleService
                .findAllByUserGroupId(command.getPojo().getUserGroup().getUserGroupId(), Constants.QUERY_NOT_EXISTS);
        mav.addObject("listOfUserRole", listOfUserRole);
        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
       return mav;
    }

    private void executeSearch(UserGroupRoleACLCommand bean, HttpServletRequest request) {
        List<UserGroupRoleACL> list = userGroupRoleACLService.findByUserGroupId(bean.getPojo().getUserGroup().getUserGroupId());
        bean.setListResult(list);
    }

}
