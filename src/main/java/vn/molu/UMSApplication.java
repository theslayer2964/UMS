package vn.molu;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import vn.molu.common.Constants;
import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UrlGroup;
import vn.molu.repository.common.impl.MyRepositoryImpl;
import vn.molu.service.admin.UrlGroupService;
import vn.molu.service.admin.UrlService;
import vn.molu.service.email.EmailSenderService;
import vn.molu.webapp.layout.MySiteMeshFilter;

import javax.servlet.ServletContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = MyRepositoryImpl.class)
@PropertySource("classpath:messages.properties")
@EnableScheduling
public class UMSApplication {

    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private ServletContext context;

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlGroupService urlGroupService;

    @Autowired
    private Environment env;

    @Autowired
    private EmailSenderService emailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(UMSApplication.class, args);
    }

    @Scheduled(cron = "${system.sendmail_time}") // synchronize all employees info at 01:00 AM every day
    public void sendEmailEveryday() {
        log.info("running daily scheduled at: (BAT MAIL)" + new Date());
//        emailSenderService.sendMailToManager_LockUserOverAccess();
//        emailSenderService.sendMailToTTCNS_General();
        System.out.println("Đã gửi xong - CHUA BAT NGHE");
    }

    @Bean
    public FilterRegistrationBean siteMeshFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new MySiteMeshFilter());
        return filter;
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            //initial user group
            if (urlGroupService.findByCode(Constants.FG_ADMIN) == null) {
                UrlGroup urlGroup = new UrlGroup();
                urlGroup.setCode(Constants.FG_ADMIN);
                urlGroup.setCreatedDate(currentTime);
                urlGroup.setFlgDelete(Constants.FLG_DELETE_ON);
                urlGroup.setName(env.getProperty("label.url_group.admin"));
                urlGroupService.save(urlGroup);
            }

            if (urlGroupService.findByCode(Constants.FG_UTILITY) == null) {
                UrlGroup urlGroup = new UrlGroup();
                urlGroup.setCode(Constants.FG_UTILITY);
                urlGroup.setCreatedDate(currentTime);
                urlGroup.setFlgDelete(Constants.FLG_DELETE_ON);
                urlGroup.setName(env.getProperty("label.url_group.utility"));
                urlGroupService.save(urlGroup);
            }

            if (urlGroupService.findByCode(Constants.FG_GUEST) == null) {
                UrlGroup urlGroup = new UrlGroup();
                urlGroup.setCode(Constants.FG_GUEST);
                urlGroup.setCreatedDate(currentTime);
                urlGroup.setFlgDelete(Constants.FLG_DELETE_ON);
                urlGroup.setName(env.getProperty("menu.guest"));
                urlGroupService.save(urlGroup);
            }

            if (urlService.findByCode(Constants.FAD_USER) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_USER);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_ADMIN);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_USER_LINK);
                url.setName(env.getProperty("label.url.user"));
                urlService.save(url);

            }

            if (urlService.findByCode(Constants.FAD_USERGROUP) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_USERGROUP);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_ADMIN);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_USERGROUP_LINK);
                url.setName(env.getProperty("label.url.user_group"));
                urlService.save(url);

            }

            if (urlService.findByCode(Constants.FAD_USERROLE) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_USERROLE);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_ADMIN);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_USERROLE_LINK);
                url.setName(env.getProperty("label.url.user_role"));
                urlService.save(url);

            }

            if (urlService.findByCode(Constants.FAD_URLGROUP) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_URLGROUP);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_ADMIN);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_URLGROUP_LINK);
                url.setName(env.getProperty("label.url.url_group"));
                urlService.save(url);

            }

            if (urlService.findByCode(Constants.FAD_URL) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_URL);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_ADMIN);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_URL_LINK);
                url.setName(env.getProperty("label.url.url"));
                urlService.save(url);

            }

            if (urlService.findByCode(Constants.FAD_USERROLEURLACL) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_USERROLEURLACL);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_ADMIN);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_USERROLEURLACL_LINK);
                url.setName(env.getProperty("label.url.user_role_url_acl"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_USERGROUPROLEACL) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_USERGROUPROLEACL);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_ADMIN);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_USERGROUPROLEACL_LINK);
                url.setName(env.getProperty("label.url.user_group_role_acl"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_USERROLEACL) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_USERROLEACL);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_ADMIN);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_USERROLEACL_LINK);
                url.setName(env.getProperty("label.url.user_role_acl"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_STATISTICLOCKUSER) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_STATISTICLOCKUSER);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_UTILITY);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_STATISTIC_LOCK_USER_LINK);
                url.setName(env.getProperty("label.url.statistic_user_lock"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_STATISTICUSER) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_STATISTICUSER);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_UTILITY);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_STATISTIC_USER_LINK);
                url.setName(env.getProperty("label.url.statistic_user"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_STATISTICSHOP) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_STATISTICSHOP);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_UTILITY);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_STATISTIC_SHOP_LINK);
                url.setName(env.getProperty("label.url.statistic_shop"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_C2_ADMIN_USER) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_C2_ADMIN_USER);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_LIST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_C2_ADMIN_USER_LINK);
                url.setName(env.getProperty("menu.system.useragency"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_AUTO_USER) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_AUTO_USER);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_LIST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_AUTO_USER_LINK);
                url.setName(env.getProperty("menu.system.c2_user_auto"));
                urlService.save(url);
            }
            if (urlService.findByCode(Constants.FAD_ADD_USER_AUTO) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_ADD_USER_AUTO);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_LIST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_ADD_USER_LINK);
                url.setName(env.getProperty("menu.system.addUser"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_ADD_USER_AUTO_FILE) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_ADD_USER_AUTO_FILE);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_LIST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_ADD_USER_FILE_LINK);
                url.setName(env.getProperty("menu.system.addUserFile"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_MANAGER_USER_FILE) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_MANAGER_USER_FILE);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_LIST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_MANAGER_USER_FILE_LINK);
                url.setName(env.getProperty("menu.system.inactivityUser"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_DEPARTMENT) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_DEPARTMENT);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_LIST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_DEPARTMENT_LINK);
                url.setName(env.getProperty("menu.system.department"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_PROGRAM) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_PROGRAM);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_LIST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_PROGRAM_LINK);
                url.setName(env.getProperty("menu.system.program"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_EXPLANATION_FORM) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_EXPLANATION_FORM);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_GUEST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_EXPLANATION_FORM_LINK);
                url.setName(env.getProperty("menu.guest.explannation-form"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_GUEST_INFO) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_GUEST_INFO);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_GUEST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_GUEST_INFO_LINK);
                url.setName(env.getProperty("menu.guest.info"));
                urlService.save(url);
            }

            if (urlService.findByCode(Constants.FAD_GUEST_REQUEST) == null) {
                Url url = new Url();
                url.setCode(Constants.FAD_GUEST_REQUEST);
                url.setCreatedDate(currentTime);
                url.setFlgDelete(Constants.FLG_DELETE_ON);
                UrlGroup urlGroup = urlGroupService.findByCode(Constants.FG_GUEST);
                url.setUrlGroup(urlGroup);
                url.setPath(Constants.FAD_GUEST_REQUEST_LINK);
                url.setName(env.getProperty("menu.guest.request"));
                urlService.save(url);
            }
        };
    }
}
