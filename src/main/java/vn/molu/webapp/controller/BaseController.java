package vn.molu.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.molu.common.utils.DesEncrypterUtils;
import vn.molu.dao.temp.C2UserAdminDAO;
import vn.molu.dto.admin.admin.C2UserAdminDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Controller
public class BaseController {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private C2UserAdminDAO c2UserAdminDAO;
    @Autowired
    private Environment env;

    @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model) {
        log.info("request url: /");

        model.put("name", getLoggedinUserName());

        List<C2UserAdminDTO> list = c2UserAdminDAO.layDS_ViPham_Homnay();
        model.addAttribute("item", list);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String username = userDetails.getUsername();
//            System.out.println("USER:" + username);
//        }
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                System.out.println("ROLE:" + role);
            }
        }
        return "welcome";
    }

    @RequestMapping("/login.html")
    public String login(@RequestParam(required = false) String message, final Model model, HttpServletRequest req) {
        log.info("request url: " + req.getRequestURI());

        if (message != null && !message.isEmpty()) {
            if (message.equals("logout")) {
                model.addAttribute("message", "Logout!");
            }
            if (message.equals("error")) {
                model.addAttribute("message", "Login Failed!");
            }
        }
        return "login";
    }

    @RequestMapping(value = "/change_password.html", method = RequestMethod.POST)
    public String change_pass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String old_pass = request.getParameter("old_pass");
        String currentURI = request.getRequestURI();
        if(old_pass == null || old_pass.trim().isEmpty()){
            request.setAttribute("old_pass_error", env.getProperty("label.c2user.message-password"));
            response.sendRedirect(currentURI);
        }

        String old_pas_encrypt = DesEncrypterUtils.getInstance().encrypt(old_pass);
        String new_pass = request.getParameter("new_password");
        if(new_pass == null || new_pass.trim().isEmpty()){
            request.setAttribute("new_pass_error", env.getProperty("label.c2user.message-password"));
            response.sendRedirect(currentURI);
        }
        String new_pas_encrypt = DesEncrypterUtils.getInstance().encrypt(new_pass);
        System.out.println("request XXX:" + old_pas_encrypt + new_pas_encrypt);
        return "redirect:/";
    }


    @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response,
                    authentication);
        }

        return "redirect:/";
    }

    @RequestMapping("/403.html")
    public String accessDenied() {
        return "403";
    }

    private String getLoggedinUserName() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }
}
