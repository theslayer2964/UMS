package vn.molu.webapp.controller.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.common.Constants;
import vn.molu.common.utils.RequestUtil;
import vn.molu.webapp.command.admin.C2AdminUserCommand;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GuestInfoController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/guest/info.html")
    public ModelAndView list2(@ModelAttribute(value = Constants.FORM_MODEL_KEY) C2AdminUserCommand command,
                              HttpServletRequest request, BindingResult bindingResult){
        RequestUtil.initSearchBean(request, command); // chua hiu lam`z`???
        ModelAndView mav = new ModelAndView("guest/info/list");
        String crudaction = command.getCrudaction();

        return mav;

    }
}
