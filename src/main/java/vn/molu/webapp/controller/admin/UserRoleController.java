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
import vn.molu.domain.admin.UserRole;
import vn.molu.service.admin.UserRoleService;
import vn.molu.webapp.command.admin.UserRoleCommand;
import vn.molu.webapp.validator.admin.UserRoleValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-17
 */
@Controller
public class UserRoleController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserRoleValidator userRoleValidator;
    @RequestMapping("/admin/userrole-list.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UserRoleCommand command,
                             HttpServletRequest request, BindingResult bindingResult){
        RequestUtil.initSearchBean(request, command);
        ModelAndView mav = new ModelAndView("admin/userrole/list");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)) { // delete action
            if (command.getPojo().getUserRoleId() != null) {
                try {
                    userRoleService.delete(command.getPojo().getUserRoleId());

                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.delete.successful"));
                } catch (Exception e) {

                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.delete.unsuccessful"));

                    log.error(e.getMessage(), e);
                }
            }
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)){ // update or insert action
            userRoleValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) {
                UserRole pojo = command.getPojo();
                if (pojo.getUserRoleId() == null) { // insert case
                    try {
                        Timestamp currentTime = new Timestamp(new Date().getTime());
                        pojo.setCreatedDate(currentTime);
                        pojo.setModifiedDate(currentTime);
                        pojo.setFlgDelete("1");

                        pojo = userRoleService.save(pojo);

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.add.successful"));
                    } catch (RuntimeException e) {

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.add.unsuccessful"));

                        log.error(e.getMessage(), e);
                    }
                } else { // update case
                    try {
                        UserRole tempUserGroup = userRoleService.findById(pojo.getUserRoleId());
                        pojo.setCreatedDate(tempUserGroup.getCreatedDate());
                        pojo.setFlgDelete(tempUserGroup.getFlgDelete());

                        Timestamp currentTime = new Timestamp(new Date().getTime());
                        pojo.setModifiedDate(currentTime);

                        pojo = userRoleService.update(pojo);

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("msg.database.update.successful"));
                    } catch (RuntimeException e) {

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful"));

                        log.error(e.getMessage(), e);
                    }
                }
                // reset modal form
                command.setPojo(null);
            } else {
                mav.addObject(Constants.IS_MODAL_SHOW, true);
            }
        }

        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }
    private void executeSearch(UserRoleCommand bean, HttpServletRequest request) {
        List<UserRole> list = userRoleService.findAll();
        bean.setListResult(list);
    }
}
