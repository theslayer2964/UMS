package vn.molu.webapp.command.admin;

import vn.molu.domain.admin.User;
import vn.molu.domain.admin.UserGroup;
import vn.molu.webapp.command.AbstractCommand;

@SuppressWarnings("serial")
public class UserCommand extends AbstractCommand<User> {

    public UserCommand(){
	    this.pojo = new User();
    }
    private String displayName;
    private Integer accountType;
    private UserGroup userGroup;

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
