package vn.molu.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LookupAttemptingCallback;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;
@Service
public class LdapUserLookupImpl implements LdapUserLookup {
    @Autowired
    private LdapTemplate ldapTemplate;
    @Value("${userDNPrefix}")
    private String userDNPrefix;
    @Value("${lastNameAttribute}")
    private String lastNameAttribute;
    @Value("${userObjectClasses}")
    private String userObjectClasses;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public boolean authenticate(String username, String password) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", this.userObjectClasses))
                .and(new EqualsFilter(userDNPrefix, username));

        return ldapTemplate.authenticate(buildDn(), filter.toString(), password, new LookupAttemptingCallback());
    }

    private Name buildDn() {
        DistinguishedName dn = new DistinguishedName();
        return dn;

    }

    @SuppressWarnings("rawtypes")
    public LdapUser getUser(String userName) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", this.userObjectClasses));
        andFilter.and(new EqualsFilter(userDNPrefix, userName));
        List list = ldapTemplate.search(DistinguishedName.EMPTY_PATH, andFilter.encode(),
                new UserAttributeMapper(userDNPrefix));
        if (list != null && list.size() > 0) {
            return (LdapUser) list.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<LdapUser> searchByDisplayName(String displayName) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", this.userObjectClasses));

        OrFilter orFilter = new OrFilter();
        orFilter.or(new LikeFilter("displayName", "*" + displayName + "*"));
        orFilter.or(new LikeFilter("mail", "*" + displayName + "*"));
        andFilter.append(orFilter);

        List<LdapUser> list = (List<LdapUser>) ldapTemplate.search(DistinguishedName.EMPTY_PATH, andFilter.encode(),
                new UserAttributeMapper(userDNPrefix));
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LdapUser> searchByDepartment(String department) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", this.userObjectClasses));

        OrFilter orFilter = new OrFilter();
        orFilter.or(new LikeFilter("title", "*" + department + "*"));
        andFilter.append(orFilter);

        List<LdapUser> list = (List<LdapUser>) ldapTemplate.search(DistinguishedName.EMPTY_PATH, andFilter.encode(),
                new UserAttributeMapper(userDNPrefix));
        return list;
    }

    public String getUserDNPrefix() {
        return userDNPrefix;
    }

    public void setUserDNPrefix(String userDNPrefix) {
        this.userDNPrefix = userDNPrefix;
    }

    public String getLastNameAttribute() {
        return lastNameAttribute;
    }

    public void setLastNameAttribute(String lastNameAttribute) {
        this.lastNameAttribute = lastNameAttribute;
    }

    public String getUserObjectClasses() {
        return userObjectClasses;
    }

    public void setUserObjectClasses(String userObjectClasses) {
        this.userObjectClasses = userObjectClasses;
    }

}
