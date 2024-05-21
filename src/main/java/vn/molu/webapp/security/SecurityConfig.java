package vn.molu.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private MyUserDetailService userDetailsService;

    @Bean
    public MyPasswordEncoder passwordEncoder() {
        MyPasswordEncoder myPasswordEncoder = new MyPasswordEncoder();
        return myPasswordEncoder;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("invoke configure method.");
        http.authorizeRequests().antMatchers("/login.html").permitAll()
                .antMatchers("/").authenticated()
                .antMatchers("/sample/**").access("hasRole('ADMIN') or hasRole('SAMPLE')")
                .antMatchers("/admin/user-list.html").access("hasAnyRole('FAD_USER','ADMIN')")
                .antMatchers("/admin/usergroup-list.html").access("hasAnyRole('FAD_USERGROUP','ADMIN','FAD_USER')")
                .antMatchers("/admin/userrole-list.html").access("hasAnyRole('FAD_USERROLE','ADMIN','FAD_USER')")
                .antMatchers("/admin/urlgroup-list.html").access("hasAnyRole('FAD_URLGROUP','ADMIN','FAD_USER')")
                .antMatchers("/admin/url-list.html").access("hasAnyRole('FAD_URL','ADMIN','FAD_USER')")
                .antMatchers("/admin/user_role_url_acl.html").access("hasAnyRole('FAD_USERROLEURLACL','ADMIN','FAD_USER')")
                .antMatchers("/admin/group_role_acl.html").access("hasAnyRole('FAD_USERGROUPROLEACL','ADMIN','FAD_USER')")
                .antMatchers("/admin/user_role_acl.html").access("hasAnyRole('FAD_USERROLEACL','ADMIN','FAD_USER')")
                .antMatchers("/utilities/user_c2_statistical.html").access("hasAnyRole('FAD_STATISLOCKUSER','ADMIN','FAD_USER')")
                .antMatchers("/utilities/user_c2_log_by_shop.html").access("hasAnyRole('FAD_STATISTICSHOP','ADMIN','FAD_USER')")
                .antMatchers("/utilities/user_c2_log.html").access("hasAnyRole('FAD_STATISTICUSER','ADMIN','FAD_USER')")
                .antMatchers("/list/user_c2_ss.html").access("hasAnyRole('ADMIN','FAD_C2_ADMIN_USER')")
                .antMatchers("/list/user_c2_auto.html").access("hasAnyRole('ADMIN','FAD_AUTO_USER')")
                .antMatchers("/list/add_user_c2.html").access("hasAnyRole('ADMIN','FAD_ADD_USER_AUTO')")
                .antMatchers("/list/add_user_c2_file.html").access("hasAnyRole('ADMIN','FAD_ADD_USER_AUTO_FILE')")
                .antMatchers("/list/manager_user_c2_file.html").access("hasAnyRole('ADMIN','FAD_MANAGER_USER_FILE')")
                .antMatchers("/list/department.html").access("hasAnyRole('ADMIN','FAD_DEPARTMENT')")
                .antMatchers("/list/program.html").access("hasAnyRole('ADMIN','FAD_PROGRAM')")
                .antMatchers("/admin/log_view.html").access("hasAnyRole('FAD_USERROLEACL','ADMIN','FAD_USER')")
                .antMatchers("/admin/log_view_c2user.html").access("hasAnyRole('FAD_USERROLEACL','ADMIN','FAD_USER')")
                .antMatchers("/admin/user-agency/user-list.html").access("hasAnyRole('FAD_USER','ADMIN')")
                .antMatchers("/guest/explanation-form.html").access("hasAnyRole('FAD_EXPLANATIONFORM','FAD_GUESTINFO','ADMIN')")
                .antMatchers("/guest/info.html").access("hasAnyRole('FAD_EXPLANATIONFORM','FAD_GUESTINFO','ADMIN')")
                .and().exceptionHandling().accessDeniedPage("/403.html")
                .and().formLogin()
                .loginProcessingUrl("/j_spring_security_login")
                .loginPage("/login.html")
                .defaultSuccessUrl("/")
                .failureUrl("/login.html?message=error")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login.html?message=logout");

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.addFilterBefore(new AuthenticationProcessingFilterExtends(authenticationManager(),authenticationSuccessHandler(),authenticationFailureHandler()), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        MyAuthenticationSuccessHandler myAuthenticationSuccessHandler= new MyAuthenticationSuccessHandler();
        return myAuthenticationSuccessHandler;
    }
    public AuthenticationFailureHandler authenticationFailureHandler(){
        MyAuthenticationFailureHandler myAuthenticationFailureHandler= new MyAuthenticationFailureHandler();
        return myAuthenticationFailureHandler;
    }

}
