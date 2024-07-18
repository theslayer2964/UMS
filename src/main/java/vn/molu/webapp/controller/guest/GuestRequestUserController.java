package vn.molu.webapp.controller.guest;

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
import vn.molu.webapp.command.guest.RequestUserCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GuestRequestUserController extends ApplicationObjectSupport {

    private transient final Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/guest/request-user.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) RequestUserCommand command,
                             HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult){
        RequestUtil.initSearchBean(request, command); // chua hiu lam`z`???
        ModelAndView mav = new ModelAndView("guest/request/insert");

        return mav;
    }
}
