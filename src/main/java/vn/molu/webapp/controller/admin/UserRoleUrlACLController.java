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
import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UrlGroup;
import vn.molu.domain.admin.UserRole;
import vn.molu.domain.admin.UserRoleUrlACL;
import vn.molu.service.admin.UrlGroupService;
import vn.molu.service.admin.UrlService;
import vn.molu.service.admin.UserRoleService;
import vn.molu.service.admin.UserRoleUrlACLService;
import vn.molu.webapp.command.admin.UserRoleUrlACLCommand;
import vn.molu.webapp.validator.admin.UserRoleUrlACLsValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserRoleUrlACLController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserRoleUrlACLService userRoleUrlACLService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlGroupService urlGroupService;

    @Autowired
    private UserRoleUrlACLsValidator userRoleUrlACLsValidator;

    @RequestMapping("/admin/user_role_url_acl.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UserRoleUrlACLCommand command,
                             HttpServletRequest request, BindingResult bindingResult) {
        RequestUtil.initSearchBean(request, command);

        ModelAndView mav = new ModelAndView("admin/user_role_url_acl/list");
        String crudaction = command.getCrudaction();

        if (command.getPojo().getUserRole().getUserRoleId() != null) {
            UserRole userRole = userRoleService.findById(command.getPojo().getUserRole().getUserRoleId());
            command.getPojo().setUserRole(userRole);
        }

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)) {
            if (command.getPojo().getUserRoleUrlACLId() != null) {
                try {
                    userRoleUrlACLService.delete(command.getPojo().getUserRoleUrlACLId());

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
            userRoleUrlACLsValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) { // insert case
                try {
                    for (int i = 0; i < command.getCheckList().length; i++) {
                        Long urlId = Long.valueOf(command.getCheckList()[i]);

                        UserRoleUrlACL userRoleUrlACL = new UserRoleUrlACL();
                        userRoleUrlACL.setUserRole(command.getPojo().getUserRole());

                        Url url = new Url();
                        url.setUrlId(urlId);
                        userRoleUrlACL.setUrl(url);

                        userRoleUrlACLService.save(userRoleUrlACL);
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

        List<Url> listOfUrl = urlService.findAllNotAssignUserRoleId(command.getPojo().getUserRole().getUserRoleId());
        mav.addObject("listOfUrl", listOfUrl);

        List<UrlGroup> listOfUrlGroupForInsert = urlGroupService.findAll();
        mav.addObject("listOfUrlGroup", listOfUrlGroupForInsert);

        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }
    private void executeSearch(UserRoleUrlACLCommand bean, HttpServletRequest request) {
        List<UserRoleUrlACL> list = userRoleUrlACLService.findByUserRoleId(bean.getPojo().getUserRole().getUserRoleId());
        bean.setListResult(list);
    }
}
