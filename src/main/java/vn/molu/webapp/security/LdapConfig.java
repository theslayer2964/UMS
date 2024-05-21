package vn.molu.webapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig  {
    @Bean
    public LdapContextSource getContextSource() throws Exception{
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://10.3.12.17:389");
        ldapContextSource.setBase("OU=C2,DC=mobifone,DC=vn");
        ldapContextSource.setUserDn("CN=forAppAuth,OU=MailSuDungChoCacChuongTrinh,OU=P.CNTT,OU=C2,DC=mobifone,DC=vn");
        ldapContextSource.setPassword("Abc@12345");
        ldapContextSource.setPooled(false);
        return ldapContextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() throws Exception{
        LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
        return ldapTemplate;
    }

}
