package vn.molu.webapp.command.admin;


import vn.molu.domain.admin.UrlGroup;
import vn.molu.webapp.command.AbstractCommand;

@SuppressWarnings("serial")
public class UrlGroupCommand extends AbstractCommand<UrlGroup> {

    public UrlGroupCommand(){
	    this.pojo = new UrlGroup();
    }
}
