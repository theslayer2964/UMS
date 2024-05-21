package vn.molu.webapp.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import vn.molu.common.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationProcessingFilterExtends extends AbstractAuthenticationProcessingFilter {
    @Value("${filterProcessesUrl}")
    private String filterProcessesUrl;
    private Boolean allowSessionCreation=true;
    public AuthenticationProcessingFilterExtends(AuthenticationManager authenticationManager, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        super(new AntPathRequestMatcher("/j_spring_security_login","POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    protected AuthenticationProcessingFilterExtends(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    private String languageParam = "location";

    public String getLanguageParam() {
        return languageParam;
    }

    public void setLanguageParam(String languageParam) {
        this.languageParam = languageParam;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim() + Constants.SECURITY_CREDENTIAL_DELIMITER + password.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Place the last username attempted into HttpSession for views
        request.getSession().setAttribute(Constants.ACEGI_SECURITY_LAST_USERNAME_KEY, username);
        String j_username = request.getParameter(Constants.ACEGI_SECURITY_FORM_USERNAME_KEY);
        request.getSession().setAttribute(Constants.ACEGI_SECURITY_FORM_USERNAME_KEY, j_username);
        String lang = null;
        lang = request.getParameter(this.languageParam);

        if (lang == null || lang.trim().equals("")) {
            lang = (String) request.getSession().getAttribute(this.languageParam);
        }
        request.getSession().setAttribute("lang", lang);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the password, such as by
     * including additional values and common.a separator.
     * <p>
     * This might be used for example if common.a postcode/zipcode was required in
     * addition to the password. A delimiter such as common.a pipe (|) should be
     * used to separate the password and extended value(s). The
     * <code>AuthenticationDao</code> will need to generate the expected password in
     * common.a corresponding manner.
     * </p>
     *
     * @param request
     *            so that request attributes can be retrieved
     *
     * @return the password that will be presented in the
     *         <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(Constants.ACEGI_SECURITY_FORM_PASSWORD_KEY);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and common.a separator.
     *
     * @param request
     *            so that request attributes can be retrieved
     *
     * @return the username that will be presented in the
     *         <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return StringUtils.trim(request.getParameter(Constants.ACEGI_SECURITY_FORM_USERNAME_KEY));
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request
     *            that an authentication request is being created for
     * @param authRequest
     *            the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


}
