package vn.molu.webapp.command.admin;

import vn.molu.domain.admin.UserRoleUrlACL;
import vn.molu.webapp.command.AbstractCommand;

@SuppressWarnings("serial")
public class UserRoleUrlACLCommand extends AbstractCommand<UserRoleUrlACL> {

    public UserRoleUrlACLCommand(){
	    this.pojo = new UserRoleUrlACL();
    }

    private Long urlGroupId;

    public Long getUrlGroupId() {
        return urlGroupId;
    }

    public void setUrlGroupId(Long urlGroupId) {
        this.urlGroupId = urlGroupId;
    }
}
