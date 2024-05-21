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
import vn.molu.domain.admin.UserGroup;
import vn.molu.service.admin.UserGroupService;
import vn.molu.webapp.command.admin.UserGroupCommand;
import vn.molu.webapp.validator.admin.UserGroupValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-17
 */
@Controller
public class UserGroupController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private UserGroupValidator userGroupValidator;

    @RequestMapping("/admin/usergroup-list.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UserGroupCommand command,
                             HttpServletRequest request, BindingResult bindingResult){
        /*RequestUtil.initSearchBean(request, command):
        Phương thức này khởi tạo đối tượng UserGroupCommand từ yêu cầu và truyền nó vào để khởi tạo model và view.
        * */
        RequestUtil.initSearchBean(request, command);
        ModelAndView mav = new ModelAndView("admin/usergroup/list");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)) { // delete action
            if (command.getPojo().getUserGroupId() != null) {
                try {
                    userGroupService.delete(command.getPojo().getUserGroupId());

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
            userGroupValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) {
                UserGroup pojo = command.getPojo();
                if (pojo.getUserGroupId() == null) { // insert case
                    try {
                        Timestamp currentTime = new Timestamp(new Date().getTime());
                        pojo.setCreatedDate(currentTime);
                        pojo.setModifiedDate(currentTime);
                        pojo.setFlgDelete("1");

                        pojo = userGroupService.save(pojo);

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
                        UserGroup tempUserGroup = userGroupService.findById(pojo.getUserGroupId());
                        pojo.setCreatedDate(tempUserGroup.getCreatedDate());
                        pojo.setFlgDelete(tempUserGroup.getFlgDelete());

                        Timestamp currentTime = new Timestamp(new Date().getTime());
                        pojo.setModifiedDate(currentTime);

                        pojo = userGroupService.update(pojo);

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("msg.database.update.successful"));
                    } catch (RuntimeException e) {
                        e.printStackTrace();

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
        // IN ra màn hình: list
        executeSearch(command, request); // find all user group
        mav.addObject(Constants.LIST_MODEL_KEY, command); // command.listResult
        return mav;
    }
    private void executeSearch(UserGroupCommand bean, HttpServletRequest request) {
        List<UserGroup> list = userGroupService.findAll();
        bean.setListResult(list);
    }
}
