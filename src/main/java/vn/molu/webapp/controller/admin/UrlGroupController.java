package vn.molu.webapp.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.common.Constants;
import vn.molu.common.utils.RequestUtil;
import vn.molu.domain.admin.UrlGroup;
import vn.molu.service.admin.UrlGroupService;
import vn.molu.webapp.command.admin.UrlGroupCommand;
import vn.molu.webapp.validator.admin.UrlGroupValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-17
 */
@Controller
public class UrlGroupController extends ApplicationObjectSupport {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private UrlGroupService urlGroupService;

    @Autowired
    private UrlGroupValidator urlGroupValidator;

    @RequestMapping("/admin/urlgroup-list.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UrlGroupCommand command, HttpServletRequest request, BindingResult bindingResult){
        RequestUtil.initSearchBean(request, command);

        ModelAndView mav = new ModelAndView("admin/urlgroup/list");
        String crudaction = command.getCrudaction();

        // Delete case
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)) {
            if (command.getPojo().getUrlGroupId() != null) {
                try {
                    urlGroupService.delete(command.getPojo().getUrlGroupId());

                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.delete.successful"));
                } catch (Exception e) {
                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.delete.unsuccessful"));

                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)) {
            urlGroupValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) {
                UrlGroup pojo = command.getPojo();
                if (pojo.getUrlGroupId() == null) { // insert case
                    try {
                        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                        pojo.setCreatedDate(currentTime);
                        pojo.setModifiedDate(currentTime);
                        pojo.setFlgDelete("1");

                        urlGroupService.save(pojo);

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.add.successful"));
                    } catch (RuntimeException e) {
                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.add.unsuccessful"));

                        log.log(Level.SEVERE, e.getMessage(), e);
                    }
                } else { // update case
                    try {
                        UrlGroup tempUserGroup = urlGroupService.findById(pojo.getUrlGroupId());
                        pojo.setCreatedDate(tempUserGroup.getCreatedDate());
                        pojo.setFlgDelete(tempUserGroup.getFlgDelete());

                        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                        pojo.setModifiedDate(currentTime);

                        urlGroupService.update(pojo);

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("msg.database.update.successful"));
                    } catch (RuntimeException e) {

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful"));

                        log.log(Level.SEVERE, e.getMessage(), e);
                    }
                }
                // reset modal form
                command.setPojo(null);
            } else {
                mav.addObject(Constants.IS_MODAL_SHOW, true);
            }
        }

        // Search case
        try {
            List<UrlGroup> listOfUrlGroup = urlGroupService.findAll();
            command.setListResult(listOfUrlGroup);
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        mav.addObject(Constants.LIST_MODEL_KEY, command);


        return mav;
    }


    @RequestMapping("/admin/urlgroup-edit.html")
    public String edit(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UrlGroupCommand command, HttpServletRequest request, BindingResult bindingResult) {
        RequestUtil.initSearchBean(request, command);

        urlGroupValidator.validate(command, bindingResult);

        String messageType = Constants.MESSAGE_TYPE_ERROR;
        String warningMsg = null;
        if (!bindingResult.hasErrors()) {
            UrlGroup pojo = command.getPojo();
            if (pojo.getUrlGroupId() == null) { // insert case
                try {
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    pojo.setCreatedDate(currentTime);
                    pojo.setModifiedDate(currentTime);
                    pojo.setFlgDelete("1");

                    urlGroupService.save(pojo);

                    messageType = Constants.MESSAGE_TYPE_SUCCESS;
                    warningMsg = this.getMessageSourceAccessor().getMessage("msg.database.add.successful");
                } catch (RuntimeException e) {
                    messageType = Constants.MESSAGE_TYPE_ERROR;
                    warningMsg = this.getMessageSourceAccessor().getMessage("msg.database.add.unsuccessful");

                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            } else { // update case
                try {
                    UrlGroup tempUserGroup = urlGroupService.findById(pojo.getUrlGroupId());
                    pojo.setCreatedDate(tempUserGroup.getCreatedDate());
                    pojo.setFlgDelete(tempUserGroup.getFlgDelete());

                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    pojo.setModifiedDate(currentTime);

                    urlGroupService.update(pojo);

                    messageType = Constants.MESSAGE_TYPE_SUCCESS;
                    warningMsg = this.getMessageSourceAccessor().getMessage("msg.database.update.successful");
                } catch (RuntimeException e) {
                    messageType = Constants.MESSAGE_TYPE_ERROR;
                    warningMsg = this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful");

                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        return "redirect:/admin/urlgroup-list.html?messageType="  + messageType + "&warningMsg=" + warningMsg;
    }
}
