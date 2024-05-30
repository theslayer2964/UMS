package vn.molu.webapp.controller.guest;

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
import vn.molu.domain.admin.ExplanationForm;
import vn.molu.domain.admin.ManagerUser;
import vn.molu.service.admin.ExplanationFormService;
import vn.molu.service.admin.ManagerUserService;
import vn.molu.service.email.EmailSenderService;
import vn.molu.webapp.command.admin.C2UserLockedCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class GuestExplanationFormController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private ExplanationFormService explanationFormService;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private EmailSenderService emailSenderService;

    @RequestMapping("/guest/explanation-form.html")
    public ModelAndView list2(@ModelAttribute(value = Constants.FORM_MODEL_KEY) C2UserLockedCommand command,
                              HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult) {
        RequestUtil.initSearchBean(request, command); // chua hiu lam`z`???
        ModelAndView mav = new ModelAndView("guest/explanationForm/insert");
        String crudaction = command.getCrudaction();

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_UPLOAD)) {
            if (!bindingResult.hasErrors()) {
                if (StringUtils.isNotBlank(command.getUploadDate())) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        // nop file giai trinh
                        ExplanationForm form = explanationFormService.uploadFile(command.getFile(), formatter.parse(command.getUploadDate()), command.getUsername().toUpperCase(), command.getForm_type());
                        // gui mail den quan tri vien
                        List<ManagerUser> managerUserList = managerUserService.findByProgramNPosition(Constants.PROGRAM_SYSTEM_UMS, Constants.MANAGER_USER_POSITION_ADMIN);
                        emailSenderService.warnAdministratorWhenUserSubmitForm(form, managerUserList, null);

                        response.sendRedirect("thanks.html");
                    } catch (Exception e) {
                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful"));
                        log.error(e.getMessage(), e);
                    }
                }
                command.setPojo(null);
            }
        }
        return mav;
    }

    @RequestMapping("/guest/thanks.html")
    public ModelAndView thanks() {
        ModelAndView mav = new ModelAndView("thanks");
        return mav;
    }
}
