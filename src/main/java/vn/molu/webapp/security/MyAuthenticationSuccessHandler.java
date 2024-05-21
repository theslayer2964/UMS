package vn.molu.webapp.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public MyAuthenticationSuccessHandler(){
        this.setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        if (isAlwaysUseDefaultTargetUrl() || StringUtils.hasText(request.getParameter(getTargetUrlParameter()))) {
            clearAuthenticationAttributes(request);

            logger.debug("Redirecting to DefaultSavedRequest Url: " + getDefaultTargetUrl());
            getRedirectStrategy().sendRedirect(request, response, getDefaultTargetUrl());
            return;
        }
    }
}
