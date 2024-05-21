package vn.molu.webapp.command.admin;


import vn.molu.domain.admin.UserGroupRoleACL;
import vn.molu.webapp.command.AbstractCommand;

@SuppressWarnings("serial")
public class UserGroupRoleACLCommand extends AbstractCommand<UserGroupRoleACL> {

    public UserGroupRoleACLCommand(){
	    this.pojo = new UserGroupRoleACL();
    }
}
