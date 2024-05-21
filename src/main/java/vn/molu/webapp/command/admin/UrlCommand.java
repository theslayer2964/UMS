package vn.molu.webapp.command.admin;


import vn.molu.domain.admin.Url;
import vn.molu.webapp.command.AbstractCommand;

@SuppressWarnings("serial")
public class UrlCommand extends AbstractCommand<Url> {

    public UrlCommand(){
	    this.pojo = new Url();
    }
}
