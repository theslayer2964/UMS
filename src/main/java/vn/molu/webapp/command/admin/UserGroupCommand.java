package vn.molu.webapp.command.admin;


import vn.molu.domain.admin.UserGroup;
import vn.molu.webapp.command.AbstractCommand;

@SuppressWarnings("serial")
public class UserGroupCommand extends AbstractCommand<UserGroup> {
    // thuộc tính pojo của lớp UserGroupCommand được sử dụng để lưu trữ đối tượng UserGroup
    public UserGroupCommand(){
    	this.pojo = new UserGroup();
    }
}
