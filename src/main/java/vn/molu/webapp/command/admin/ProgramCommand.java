package vn.molu.webapp.command.admin;

import vn.molu.domain.admin.Program;
import vn.molu.webapp.command.AbstractCommand;

public class ProgramCommand extends AbstractCommand<Program> {
    public ProgramCommand() {
        this.pojo = new Program();
    }
}
