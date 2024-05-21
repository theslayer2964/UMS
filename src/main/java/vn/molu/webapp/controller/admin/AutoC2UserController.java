package vn.molu.webapp.controller.admin;

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
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.service.admin.AutoC2UserService;
import vn.molu.webapp.command.admin.AutoC2UserCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AutoC2UserController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private AutoC2UserService autoC2UserService;

    @RequestMapping("/list/user_c2_auto.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY)AutoC2UserCommand command,
                             HttpServletRequest request, BindingResult bindingResult){
        RequestUtil.initSearchBean(request, command);
        ModelAndView mav = new ModelAndView("admin/autoC2User/list");

        executeSearch(command);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(AutoC2UserCommand command) {
        List<C2AdminUserAuto> list = autoC2UserService.findAll();
        command.setListResult(list);
    }
}
