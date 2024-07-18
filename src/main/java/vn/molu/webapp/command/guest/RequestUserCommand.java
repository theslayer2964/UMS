package vn.molu.webapp.command.guest;

import vn.molu.dto.admin.guest.ProposeDTO;
import vn.molu.webapp.command.AbstractCommand;

public class RequestUserCommand extends AbstractCommand<ProposeDTO> {
    public RequestUserCommand() {
        this.pojo = new ProposeDTO();
    }
}
