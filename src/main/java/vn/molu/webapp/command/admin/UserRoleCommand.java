package vn.molu.webapp.command.admin;


import vn.molu.domain.admin.UserRole;
import vn.molu.webapp.command.AbstractCommand;

@SuppressWarnings("serial")
public class UserRoleCommand extends AbstractCommand<UserRole> {
    
    public UserRoleCommand(){
	    this.pojo = new UserRole();
    }

}
