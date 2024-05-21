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
import vn.molu.dao.temp.C2UserAdminDAO;
import vn.molu.domain.admin.ExplanationForm;
import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.dto.admin.admin.ViewLogDTO;
import vn.molu.service.admin.ExplanationFormService;
import vn.molu.service.admin.ViewLogService;
import vn.molu.webapp.command.admin.C2UserLockedCommand;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class C2UserLockedController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private ExplanationFormService explanationFormService;
    @Autowired
    private C2UserAdminDAO c2UserAdminDAO;
    @Autowired
    private ViewLogService viewLogService;

    @RequestMapping("/utilities/user_c2_statistical.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) C2UserLockedCommand command,
                             HttpServletRequest request, BindingResult bindingResult) throws IOException, ParseException {
        RequestUtil.initSearchBean(request, command); // chua hiu lam`z`???
        ModelAndView mav = new ModelAndView("admin/utilities/listC2UserLocked");
        String crudaction = command.getCrudaction();

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_UPLOAD)) { // upload
//            c2StatisticalValidator.validate(command, bindingResult);
            if (!bindingResult.hasErrors()) {
                if (StringUtils.isNotBlank(command.getUploadDate())) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        explanationFormService.uploadFile(command.getFile(), formatter.parse(command.getUploadDate()), command.getUsername(), command.getForm_type());
                        c2UserAdminDAO.updateIP(command.getUsername(), command.getGranted_ip());

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                this.getMessageSourceAccessor().getMessage("msg.database.update.successful"));
                    } catch (Exception e) {
                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful"));
                        log.error(e.getMessage(), e);
                    }
                }
                command.setPojo(null);
            } else {
                mav.addObject(Constants.IS_MODAL_SHOW, true);
            }
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_UNLOCK)) {
            Long id = command.getPojo().getUserId();
            if (id != null) {
                try {
                    Integer rs = c2UserAdminDAO.updateStatusC2UserAdmin(id, "1");
                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                            this.getMessageSourceAccessor().getMessage("msg.database.unlock.successful"));
                } catch (Exception e) {
                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                            this.getMessageSourceAccessor().getMessage("msg.database.unlock.unsuccessful"));

                    log.error(e.getMessage(), e);
                }
            }
            command.setPojo(null);
        }
        executeSearch(command);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(C2UserLockedCommand command) {
        List<C2UserAdminDTO> listC2AdminViPham = c2UserAdminDAO.layDS_ViPham_TanSuat_TraCuu_All();
        List<String> listUserSubmittedForm = explanationFormService.findExplanationFormByStatus(Constants.EXPLANATION_FORM_UNLOCK, Constants.EXPLANATION_FORM_STATUS_SUBMIT);
        command.setListResult(updateStatusC2UserAdminDTO(listC2AdminViPham, listUserSubmittedForm));
    }

    private List<C2UserAdminDTO> updateStatusC2UserAdminDTO(List<C2UserAdminDTO> listC2AdminViPham, List<String> listUserSubmittedForm) {
        if (listUserSubmittedForm.size() > 0) {
            for (C2UserAdminDTO c2UserAdminDTO : listC2AdminViPham) {
                if (listUserSubmittedForm.contains(c2UserAdminDTO.getUsername())) {
                    c2UserAdminDTO.setStatus(Constants.EXPLANATION_FORM_NOTE_USER_SUBMIT);
                }
            }
        }
        return listC2AdminViPham;
    }

    @RequestMapping(value = "/admin/log_view2.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<ViewLogDTO> test(@RequestParam("username") String username,
                                               @RequestParam("from") String from,
                                               @RequestParam("to") String to) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String searchFrom =
                    from == null || from.trim().equals("") ? formatter.format(LocalDate.now().minusDays(3)).toString() : from;
            String searchTo =
                    to == null || to.trim().equals("") ? formatter.format(LocalDate.now()).toString() : to;
            List<ViewLogDTO> list = viewLogService.lay_LichSu_TraCuuLog_TheoTen_Thoigian(username, searchFrom, searchTo);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/admin/explanation_history.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<ExplanationForm> getHistoryByUser(@RequestParam("username") String username) {
        try {
            return explanationFormService.findExplanationFormByUser_nameNFormtype(username, Integer.parseInt(Constants.EXPLANATION_FORM_UNLOCK));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/admin/updateFormStatus.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody ExplanationForm updateForm(@RequestParam("id") Long id,
                                                    @RequestParam("status") Integer newStatus){
        try {
            explanationFormService.updateStatusFormById(id, newStatus);
            return explanationFormService.findById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
