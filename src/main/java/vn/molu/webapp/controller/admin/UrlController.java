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
import vn.molu.service.admin.UrlGroupService;
import vn.molu.service.admin.UrlService;
import vn.molu.webapp.command.admin.UrlCommand;
import vn.molu.webapp.validator.admin.UrlValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-17
 */
@Controller
public class UrlController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlGroupService urlGroupService;

    @Autowired
    private UrlValidator urlValidator;

    @RequestMapping("/admin/url-list.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) UrlCommand command,
                             HttpServletRequest request, BindingResult bindingResult){
        RequestUtil.initSearchBean(request, command);
        ModelAndView mav = new ModelAndView("admin/url/list");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)) { // delete action
            if (command.getPojo().getUrlId() != null) {
                try {
                    urlService.delete(command.getPojo().getUrlId());

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
            urlValidator.validate(command, bindingResult);

            if (!bindingResult.hasErrors()) {
                Url pojo = command.getPojo();
                if (pojo.getUrlId() == null) { // insert case
                    try {
                        Timestamp currentTime = new Timestamp(new Date().getTime());
                        pojo.setCreatedDate(currentTime);
                        pojo.setModifiedDate(currentTime);
                        pojo.setFlgDelete("1");
                        if (pojo.getUrlGroup().getUrlGroupId() == -1L) {
                            pojo.setUrlGroup(null);
                        }

                        pojo = urlService.save(pojo);

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
                        Url tempUrl = urlService.findById(pojo.getUrlId());
                        pojo.setCreatedDate(tempUrl.getCreatedDate());
                        pojo.setFlgDelete(tempUrl.getFlgDelete());

                        Timestamp currentTime = new Timestamp(new Date().getTime());
                        pojo.setModifiedDate(currentTime);
                        if (pojo.getUrlGroup().getUrlGroupId() == -1L) {
                            pojo.setUrlGroup(null);
                        }

                        pojo = urlService.update(pojo);

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

        executeSearch(command, request);
        List<UrlGroup> listOfUrlGroup = urlGroupService.findAll();

        mav.addObject("listOfUrlGroup", listOfUrlGroup);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }
    private void executeSearch(UrlCommand bean, HttpServletRequest request) {
        List<Url> list = urlService.findAll();
        bean.setListResult(list);
    }
}
