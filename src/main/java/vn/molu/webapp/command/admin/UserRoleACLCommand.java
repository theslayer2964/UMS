package vn.molu.webapp.command.admin;


import vn.molu.domain.admin.UserRoleACL;
import vn.molu.webapp.command.AbstractCommand;

@SuppressWarnings("serial")
public class UserRoleACLCommand extends AbstractCommand<UserRoleACL> {

    public UserRoleACLCommand(){
	    this.pojo = new UserRoleACL();
    }
}
