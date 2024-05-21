package vn.molu.webapp.security;

import java.util.List;

public interface LdapUserLookup {

    public boolean authenticate(String username, String password);

    public LdapUser getUser(String userName);

    public List<LdapUser> searchByDisplayName(String displayName);

    public List<LdapUser> searchByDepartment(String department);

}