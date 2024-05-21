package vn.molu.webapp.command.admin;

import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.webapp.command.AbstractCommand;

public class AutoC2UserCommand extends AbstractCommand<C2AdminUserAuto> {
    public AutoC2UserCommand() {
        this.pojo = new C2AdminUserAuto();
    }
}
